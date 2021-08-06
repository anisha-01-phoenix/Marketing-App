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
    private List<Item> itemsList;
    private String orderStatus;
    private String date;
    private String time;
    private String price;
    
Item :-

    private String productName;
    private String quantity;
    
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
