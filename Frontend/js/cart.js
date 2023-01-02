let userObj=JSON.parse(localStorage.getItem("user")) || [];
let cartArr=[];
for(let i=0;i<userObj.orders.length;i++){
    if(userObj.orders[i].orderStatus=="PENDING"){
        cartArr=userObj.orders[i].productOrderDetails;
        // console.log(cartArr);
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

function displayProducts(){
    
}

function goToHome(){
    window.location.href="index.html";
}