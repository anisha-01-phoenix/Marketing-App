package com.example.marketingapp.map

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.marketingapp.Dashboard
import com.example.marketingapp.R
import com.example.marketingapp.classes.Coordinates
import com.example.marketingapp.classes.Shopkeeper
import com.example.marketingapp.classes.User
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
import es.dmoral.toasty.Toasty
import java.util.*
import java.util.jar.Manifest

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMyLocationChangeListener, GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var marker : Marker? = null
    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var fabSetShopLocation : Button
    private lateinit var shopname : String
    private lateinit var shoptype : String
    private var shopkeeper : Shopkeeper? = null
    private lateinit var coordinates: Coordinates
    private var usertype : Int? = null // 1 -> shopkeeper, 2 -> customer
    private  lateinit var lastLocation : Location


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fabSetShopLocation = findViewById<Button>(R.id.fab)
        fabSetShopLocation.setOnClickListener(this)

        if(intent.hasExtra("shopkeeper")) {
            shopkeeper = intent.getSerializableExtra("shopkeeper") as Shopkeeper
            shopname = shopkeeper?.shopName ?: "Shop Name"
            shoptype = shopkeeper?.shopCategory ?: "Shop Type"
            usertype = 1
        }else {
            usertype = 2
        }

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

        requestPermission()
        if(usertype==1){
            onMapClick(map)
        }else {
            onMyLocationChange(lastLocation)
        }
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
                fusedLocationClient?.lastLocation!!.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful && task.result != null) {
                        lastLocation = task.result
                        val zoomlevel = 25f
                        val crntLatLng =   LatLng(lastLocation.latitude, lastLocation.longitude)
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(crntLatLng,zoomlevel))
                    }
                    else {
                        Log.w(TAG, "getLastLocation:exception", task.exception)
                        Toasty.error(this,"No location detected. Make sure location is enabled on the device.").show()
                    }
                }
            }
        })
    }

    // fab clicked
    override fun onClick(v: View?) {
        // function for fab
        val progressDialog : ProgressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Setting your shop")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
        var mAuth : FirebaseAuth = FirebaseAuth.getInstance()
        var uid = mAuth.uid
        shopkeeper?.coordinates = coordinates
        var reff : DatabaseReference
        if(shopkeeper?.isWholeSeller!!.equals(false))
            reff = FirebaseDatabase.getInstance().getReference("Shopkeeper").child(uid!!)
        else
            reff = FirebaseDatabase.getInstance().getReference("Wholesellers").child(uid!!)

        reff.setValue(shopkeeper)

        val reference1 : DatabaseReference = FirebaseDatabase.getInstance().getReference("ShopPhNo")
        reference1.push().setValue(shopkeeper!!.phoneNo)

        progressDialog.cancel()
        val intent = Intent(this, Dashboard::class.java)
        startActivity(intent)
        finish()
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
                        marker2.tag = child.key
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }

        map.setOnMarkerClickListener(this)

        reff.addValueEventListener(valueEventListener)
    }

    class ShopDialogFragment(val shopkeeper: Shopkeeper) : DialogFragment() {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let {
                val builder = AlertDialog.Builder(it)

                val inflater = requireActivity().layoutInflater

                val view = inflater.inflate(R.layout.map_dialog_box, null)
                view.findViewById<TextView>(R.id.shop_name_txt_view).text = shopkeeper.shopName
                view.findViewById<TextView>(R.id.shop_type_txt_view).text = shopkeeper.shopCategory
                view.findViewById<Button>(R.id.navigate_to_shop_btn).setOnClickListener {
                    // navigate to shop
                }
                view.findViewById<Button>(R.id.open_shop_btn).setOnClickListener {
                    // open shop
                }
                view.findViewById<Button>(R.id.cancel_btn).setOnClickListener {
                    // cancel
                    this.dismiss()
                }

                builder.setView(view)

                builder.create()
            }?: throw IllegalStateException("Activity cannot be null")
        }
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        val reff = FirebaseDatabase.getInstance().getReference("Shopkeepers")
        lateinit var shopkeeper : Shopkeeper

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child : DataSnapshot in snapshot.children){
                    if (child.key.equals(p0.tag.toString())){
                        shopkeeper = child.value as Shopkeeper
                        break
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        reff.addValueEventListener(valueEventListener)

        val newFragment = ShopDialogFragment(shopkeeper)
        newFragment.show(supportFragmentManager, "shop")

        return false
    }


}


private const val REQUEST_TURN_DEVICE_LOCATION_ON = 29
private const val TAG = "MapsActivity"