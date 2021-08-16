# Marketing-App

Shopkeeper :-

    private String uniqueId;
    private String shopName;
    private String address;
    private String email;
    private String phoneNo;
    private long rating;
    private boolean isWholeSeller;
    private Coordinates coordinates;
    
Order :-

    private String uniqueId;
    private String uniqueIdShop;
    private String uniqueIdUser;
    private String productName;
    private String quantity;
    private int orderStatus; 
    
    // orderStatus=0 -> Available
    // orderStatus=1 -> Pending
    // orderStatus=2 -> Processing
    // orderStatus=3 -> Not Available
    
    private String date;
    private String time;
    private String price;
    
    
ShopContent :-

    private String shopId;
    private List<ShopItem> list;
    
ShopItem :-

    private String productName;
    private String price;
    private String quantityAvailable;
    
User :-

    private String uniqueId;
    private String email;
    private Coordinates coordinates;
    private List<String> favShops;
    
Coordinates :-

    private double latitude;
    private double longitude;


saurav-
shop_content_recview with addition of data to realtime database by putting edit text in bottom fragment.
package(shopkeeper)=shopcontent.java,modelcontent_fbase.java,model_shopcontent.java,shopcontentadapter.java,shopkeeper.bottom_frag_add_items.java,
