# Marketing-App

## About the App :
An Application that benefits both Shopkeepers and Customers from the daily chaos that they encounter in their Shops. 

## Authentication System :
A User can register themselves into the App either as a Shopkeeper or as a Customer. The Authentication is carried out using the Phone Number Verification Code. The Shopkeeper needs to provide details regarding the Shop Name, Shop Address, Type of goods sold in the shop, and whether the shopkeeper is a wholesaler or a retailer. Following this, the shopkeeper needs to set the shop coordinates on a Map. This will be used later by the Customer to find the nearby shops.

After that, the User will reach the Dashboard.

## Shopkeeper :
 The various Features available to a Shopkeeper include:
 
     1) Change Order Status of the Orders placed in the Shop
     2) View his Shop Products
     3) Add/Edit the details of the Products
     4) View Customer's reviews and ratings regarding the Shop and its Products.
 
The Shopkeeper's Dashboard will display the list of Orders that have been placed in the Shop. The Shopkeeper will have an Option to view all the Products present in the Shop. He can change the status of the Orders from Available to Pending, or Processing. The respective Customers will get to know about the Order Status accordingly. The Shopkeeper can further add Products/ edit the already added Products. While Adding Products, the Shopkeeper needs to Upload a picture of the product and then set the Product price and Offers and Discounts available on the respective Product(if any). 

## Customer :
The various Features available to a Customer include:

     1) View and Search for Products available in all the registered Shops.
     2) Add Items to Cart
     3) Place Orders
     4) Give Reviews and Ratings to the Shopkeeper's
     5) Check the status of already placed Orders
 
The Customer's Dashboard displays the list of all the Products along with the Shop names. The Customer can search for the particular product that is required. The Product details, along with the Product ratings can be viewed. Accordingly, the Customer can add products to the Cart and finally place Orders. There is an option to remove products from the cart as well.
There is a Navigation Drawer with the following options:

        1) Maps: From here the Customer can view all the nearby stores registered in the App
        2) My Orders: From here the Customer can check the status of the Orders which has already been placed and rate the Shop accordingly.
    
    
  # Technology Stack :
  
  ### Platform :
       Android Studio (Java,Kotlin and XML)
       Adobe XD (for the App UI)
   
  ### Version Control :
       Github for Collaboration
   
  ### Tech Stack :
  
      Firebase Authentication
      Firebase Database
      Firebase Cloud Storage
      Shared Preferences
      
  ### Libraries Used :
       Lottie Animations    (implementation "com.airbnb.android:lottie:3.4.0")
       Toasty               (implementation 'com.github.GrenderG:Toasty:1.5.0')
       Glide                (implementation 'com.github.bumptech.glide:glide:4.12.0')
       Dexter               (implementation 'com.karumi:dexter:6.2.2')
       Country Code Picker  (implementation 'com.hbb20:ccp:2.5.4')
       Photo View           (implementation 'com.github.chrisbanes:PhotoView:2.3.0')

