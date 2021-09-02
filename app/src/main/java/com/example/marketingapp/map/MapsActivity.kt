package com.example.marketingapp.map

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.marketingapp.R
import com.example.marketingapp.classes.Coordinates
import com.example.marketingapp.classes.Shopkeeper
import com.example.marketingapp.databinding.ActivityMapsBinding
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import java.util.jar.Manifest

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMyLocationChangeListener {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var marker : Marker? = null
    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var fabSetShopLocation : Button
    private lateinit var shopname : String
    private lateinit var shoptype : String
    private lateinit var shopkeeper : Shopkeeper
    private lateinit var coordinates: Coordinates


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fabSetShopLocation = findViewById<Button>(R.id.fab)
        fabSetShopLocation.setOnClickListener(this)

        shopkeeper = intent.getSerializableExtra("shopkeeper") as Shopkeeper
        shopname = shopkeeper?.shopName ?: "Shop Name"
        shoptype = shopkeeper?.shopCategory ?: "Shop Type"

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        onMapClick(map)
        requestPermission()
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == REQUEST_LOCATION_PERMISSION){
            enableMyLocation()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TURN_DEVICE_LOCATION_ON) {
            checkDeviceLocationSettings(false)
        }

        if(requestCode == REQUEST_LOCATION_PERMISSION) {
            enableMyLocation()
        }
    }

    private fun onMapClick(map : GoogleMap){
        map.setOnMapClickListener { latLng ->
            val snippet = String.format(Locale.getDefault(),shoptype)

            marker?.remove()
            marker = map.addMarker(
                MarkerOptions().position(latLng)
                    .title(shopname)
                    .snippet(snippet)
            )
            marker?.showInfoWindow()
            marker?.isDraggable = true

            coordinates = Coordinates(marker?.position!!.latitude,marker?.position!!.longitude)

            fabSetShopLocation.visibility = View.VISIBLE
        }

    }

    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(this ,
            android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if(isPermissionGranted()){
            map.isMyLocationEnabled = true
            checkDeviceLocationSettings()
        }
        else{
            Snackbar.make(
                findViewById(R.id.map),
                R.string.location_required_error, Snackbar.LENGTH_INDEFINITE
            ).setAction(android.R.string.ok) {
                requestPermission()
            }.show()
        }
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(this,
            arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
    }

    @SuppressLint("MissingPermission")
    private fun checkDeviceLocationSettings(resolve:Boolean = true) {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_LOW_POWER
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val settingsClient = LocationServices.getSettingsClient(this)
        val locationSettingsResponseTask =
            settingsClient.checkLocationSettings(builder.build())

        locationSettingsResponseTask.addOnFailureListener { exception ->
            if (exception is ResolvableApiException && resolve) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(
                        this@MapsActivity,
                        REQUEST_TURN_DEVICE_LOCATION_ON
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    Log.d(
                        com.example.marketingapp.map.TAG,
                        "Error getting location settings resolution: " + sendEx.message
                    )
                }
            } else {
                Snackbar.make(
                    findViewById(R.id.map),
                    R.string.location_required_error, Snackbar.LENGTH_INDEFINITE
                ).setAction(android.R.string.ok) {
                    checkDeviceLocationSettings()
                }.show()
            }
        }
        locationSettingsResponseTask.addOnCompleteListener(OnCompleteListener {
            if(it.isSuccessful){
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location : Location? ->
                            // Got last known location. In some rare situations this can be null.
                            val zoomlevel = 15f

                            val snippet = String.format(Locale.getDefault(),"Shop Type")
                            val crntLatLng = location?.latitude?.let { LatLng(it,
                                location.longitude
                            ) }
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(crntLatLng,zoomlevel))
                        }
            }
        })
    }

    override fun onClick(v: View?) {
        // function for fab
        val progressDialog : ProgressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Setting your shop")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
        shopkeeper.coordinates = coordinates
        var reff : DatabaseReference
        if(shopkeeper.isWholeSeller.equals(false))
            reff = FirebaseDatabase.getInstance().getReference("Shopkeepers")
        else
            reff = FirebaseDatabase.getInstance().getReference("Wholesellers")

        reff.push().setValue(shopkeeper)
        progressDialog.cancel()
    }

    override fun onMyLocationChange(p0: Location) {
        var shop : Location =  Location("New Shop")
        // loop thru shops and set shop to countered shop's location and implement if(p0.distanceTo(shop) < METERS_100)
        // check if user is shopkeeper -> show wholeseller and if user is customer -> show shops
        val reff : DatabaseReference = FirebaseDatabase.getInstance().getReference("Shopkeepers")

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child : DataSnapshot in snapshot.children){
                    var coord : Coordinates = child.child("coordinates").value as Coordinates
                    shop.latitude = coord.latitude
                    shop.longitude = coord.longitude
                    if(p0.distanceTo(shop) <= 200){
                        val snippet = String.format(Locale.getDefault(),child.child("shopCategory").value as String)

                        val latLng : LatLng = LatLng(shop.latitude,shop.longitude)
                        val marker2 : Marker = map.addMarker( MarkerOptions().position(latLng).title(child.child("shopName").value as String).snippet(snippet))
                        marker2.showInfoWindow()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }

        reff.addValueEventListener(valueEventListener)
    }
}


private const val REQUEST_TURN_DEVICE_LOCATION_ON = 29
private const val TAG = "MapsActivity"