let token=localStorage.getItem("token");
let userObj=JSON.parse(localStorage.getItem("user")) || [];
async function checkLogin(){
    let t=token;
    try{
        let res=await fetch("http://localhost:8880/user/login/details/"+t,{
            method:'GET',
            headers:{
                "Content-Type":"application/json"
            }
        });
            let data=await res.json();
            if(data.message==undefined){
                userObj=data;
                localStorage.setItem("user",JSON.stringify(userObj));
                for(let i=0;i<userObj.orders.length;i++){
                    if(userObj.orders[i].orderStatus=="PENDING"){
                        cartArr=userObj.orders[i].productOrderDetails;
                        displayProducts(cartArr);
                    }
                }
                location.reload();
                // document.getElementById("login").innerHTML="Logout";
            }else{
                alert(data.message);
            }
    }catch(err){
        console.log(err);
    }
}

let cartArr=[];
let order=null;
for(let i=0;i<userObj.orders.length;i++){
    if(userObj.orders[i].orderStatus=="PENDING"){
        cartArr=userObj.orders[i].productOrderDetails;
        // console.log(cartArr);
        order=userObj.orders[i];
        displayProducts(cartArr);
    }
}
checkCart();
function checkCart(){
    if(cartArr.length==0){
        document.getElementById("emptyCart").style.display="block";
        document.getElementById("nonEmptyCart").style.display="none";
    }else{
        document.getElementById("emptyCart").style.display="none";
        document.getElementById("nonEmptyCart").style.display="flex";
    }
}

function displayProducts(arr){
    
    document.getElementById("products").innerHTML="";
    let img="https://icon2.cleanpng.com/20180605/ijl/kisspng-computer-icons-image-file-formats-no-image-5b16ff0d2414b5.0787389815282337411478.jpg";
    let data=``;
    arr.forEach(e => {
        let img="https://icon2.cleanpng.com/20180605/ijl/kisspng-computer-icons-image-file-formats-no-image-5b16ff0d2414b5.0787389815282337411478.jpg";
        if(e.product.imgs.length>0){
            img=e.product.imgs[0].link;
        }
        data +=`<div>
        <div>
            <img src="${img}" alt="">
        </div>
        <p>${e.product.brand}</p>
        <h3>${e.product.name}</h3>
        <p>Price:- ${e.product.sellPrice}</p>
        <p>Quantity:- ${e.quantity}</p>
        <button onclick="removeFromCart(${e.productOrderDetailId})">Remove</button>
    </div>`
    });
    document.getElementById("total").innerHTML=order.totalamount;
    if(order.address==null){
        let addresses=`<option value="">Select Address here</option>`;
        userObj.addresses.forEach(e => {
            addresses+=`<option value="${e.addressId}">${e.houseNumber}, ${e.city}, ${e.state}</option>`
        });
        document.getElementById("address").innerHTML=addresses;
    }else{
        let temp=order.address.addressId;
        let addresses=`<option value="${order.address.addressId}">${order.address.addressId}">${order.address.houseNumber}, ${order.address.city}, ${order.address.state}</option>`;
        userObj.addresses.forEach(e => {
            if(temp!=e.addressId){
                addresses+=`<option value="${e.addressId}">${e.houseNumber}, ${e.city}, ${e.state}</option>`
            }
        });
        document.getElementById("address").innerHTML=addresses;
    }
    if(order.promocode==null){
        document.getElementById("overTotal").innerHTML=order.totalamount;
    }else{
        document.getElementById("discount").innerHTML=order.promocode.amt;
        document.getElementById("overTotal").innerHTML=order.totalamount-order.promocode.amt;
        document.getElementById("promocode1").innerHTML="Remove";
        document.getElementsByName("promocode")[0].placeholder="Promocode Applied";
        document.getElementById("promocode").readOnly=true;
        document.getElementById("promocode").style.cursor="not-allowed";
    }
    

    document.getElementById("products").innerHTML=data;
}

async function removeFromCart(id){
    try{
        let res=await fetch("http://localhost:8880/user/order/removeFromCart/"+order.orderId+"/"+id,{
            method:'PUT',
            headers:{
                "Content-Type":"application/json"
            }
        });
            let data=await res.json();
            if(data.message==undefined){
                displayProducts(cartArr);
                checkLogin();
            }else{
                alert(data.message);
                console.log(data);
            }
    }catch(err){
        console.log(err);
    }
}

async function applyPromo(){

    if(order.promocode==null){
        let promo=document.getElementById("promocode").value;
        try{
            let res=await fetch("http://localhost:8880/user/order/applyPromo/"+order.orderId+"/"+promo,{
                method:'PUT',
                headers:{
                    "Content-Type":"application/json"
                }
            });
                let data=await res.json();
                if(data.message==undefined){
                    displayProducts(cartArr);
                    alert("Promocode Applied Successfull!!");
                    checkLogin();
                }else{
                    alert(data.message);
                }
        }catch(err){
            console.log(err);
        }
    }else{
        try{
            let res=await fetch("http://localhost:8880/user/order/removePromo/"+order.orderId,{
                method:'PUT',
                headers:{
                    "Content-Type":"application/json"
                }
            });
                let data=await res.json();
                if(data.message==undefined){
                    displayProducts(cartArr);
                    alert("Promocode Removed Successfull!!");
                    checkLogin();
                }else{
                    alert(data.message);
                }
        }catch(err){
            console.log(err);
        }
    }
    
}

async function selectAddress(){
    let addressValue=document.getElementById("address").value;
    try{
        let res=await fetch("http://localhost:8880/user/order/addAddress/"+order.orderId+"/"+addressValue,{
            method:'PUT',
            headers:{
                "Content-Type":"application/json"
            }
        });
            let data=await res.json();
            if(data.message==undefined){
                displayProducts(cartArr);
                alert("Address changed Successfull!!");
                checkLogin();
            }else{
                alert(data.message);
            }
    }catch(err){
        console.log(err);
    }
}

async function confirmOrder(){
    try{
        let res=await fetch("http://localhost:8880/user/order/confirm/"+userObj.userId+"/"+order.orderId,{
            method:'PUT',
            headers:{
                "Content-Type":"application/json"
            }
        });
            let data=await res.json();
            if(data.message==undefined){
                displayProducts(cartArr);
                alert("Order Confirmed Successfull!!");
                checkLogin();
            }else{
                alert(data.message);
            }
    }catch(err){
        console.log(err);
    }
}


function goToHome(){
    window.location.href="index.html";
}