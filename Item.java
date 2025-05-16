public class Item{
private String itemId;
private String name;
private int quantity;
private double price;
private int soldCount;


public Item(){
price=0.0;
itemId="";
name="";
soldCount=0;
quantity=0;
}

public Item(String itemId, String name, int quantity, double price){
this.itemId=itemId;
soldCount=0;
this.name=name;
this.quantity=quantity;
this.price=price;
}


public Item(String itemId, String name, int quantity, double price, int soldCount) {
    this.itemId = itemId;
    this.name = name;
    this.quantity = quantity;
    this.price = price;
    this.soldCount = soldCount;
}
public boolean updateStock(int purchaseQuantity){
if(quantity>=purchaseQuantity){
quantity-=purchaseQuantity;
soldCount+=purchaseQuantity;
 return true;
 }
 else
 return false;
}

public double getTotalCost(int purchasedQuantity){
return price * purchasedQuantity;
}


public void display(){
System.out.printf("%-10s |%-30s |%-10d |SR%-13.2f |%-10d%n", itemId,name,quantity,price,soldCount);
}

public void setItemid(String id){


itemId=id;
}

public void setQuantity(int quantity){
this.quantity=quantity;
}

public void setName(String name){
this.name=name;
}

public void setPrice(double price){
this.price=price;
}

public void setSoldCount(int count){
soldCount=count;
}

//get

 public String getItemId(){
 return itemId;
 }
 
 public int getQuantity(){
 return quantity;
 }
 
 public String getName(){
 return name;
 }
 
 public double getPrice(){
 return price;
 }
 
 public int getSoldCount(){
 return soldCount;
 }

 
 }