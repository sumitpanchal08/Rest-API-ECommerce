let token=localStorage.getItem("token");
let userObj=JSON.parse(localStorage.getItem("user")) || [];

// console.log(userObj);
if(token!=null){
    checkLogin(token);
}
async function checkLogin(token){
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
                document.getElementById("login").innerHTML="Logout";
            }else{
                alert(data.message);
            }
    }catch(err){
        console.log(err);
    }
}
async function logout(){
    let t=token;
    try{
        let res=await fetch("http://localhost:8880/user/logout/"+t,{
            method:'DELETE',
            headers:{
                "Content-Type":"application/json"
            }
        });
            let data=await res.json();
            if(data.message!=undefined){
                alert(data.message);
            }
    }catch(err){
        console.log(err);
    }
}
function goHome(){
    document.getElementById("home").style.display="block";
    document.getElementById("products").style.display="none";
    document.getElementById("address").style.display="none";
}
function goProduct(){
    document.getElementById("home").style.display="none";
    document.getElementById("products").style.display="flex";
    document.getElementById("address").style.display="none";
    getAllProducts();
    getAllCategories();
}
function goAddress(){
    if(token==null){
        alert("Login First!!");
    }else{
        document.getElementById("home").style.display="none";
    document.getElementById("products").style.display="none";
    document.getElementById("address").style.display="flex";
    showAddress();
    }
    
}
function login(){
    let loginDetails=document.getElementById("login").innerHTML;
    if(loginDetails=="Login" && token==null ){
        window.location.href="login.html";
        console.log(1);
    }else if(loginDetails=="Logout" && token!=null){
        //logout
        document.getElementById("login").innerHTML="Login";
        logout();
        localStorage.clear();
        location.reload();
        console.log(2);
    }else{
        console.log(3);
    }
}

function addToCart(id){

}



function addAddress(){
let addAddressForm=document.getElementById("addNewAddress");
    let obj={
        'name':addAddressForm.name.value,
        'mobile':addAddressForm.mobile.value,
        'houseNumber':addAddressForm.house.value,
        'city':addAddressForm.city.value,
        'state':addAddressForm.state.value,
        'landmark':addAddressForm.land.value,
        'postalCode':addAddressForm.pin.value
    }
    add();
    async function add(){
        let options = {
                method: "PUT",
                headers: {
                        "Content-type": "application/json"
                },
                body: JSON.stringify(obj)
        }
        let p = await fetch('http://localhost:8880/user/address/'+userObj.userId, options);
        let res = await p.json()
        console.log(res);
        if(res.message!=undefined){
            alert(res.message);
        }else{
            alert("Address added Successfully");
        }
    };
    
}
// function to Show Addresses..
function showAddress(){
    let addressObj=userObj.addresses;
    document.getElementById("listAddress").innerHTML="";
    let data=``;
    addressObj.forEach(e=>{
        data += `<div>
        <h3>${e.name}</h3>
        <p>AddressID:- ${e.addressId}</p>
        <p>Mobile:- ${e.mobile}</p>
        <p>LandMark:- ${e.landmark}</p>
        <p>Address:- ${e.houseNumber}, ${e.city}, ${e.state}</p>
        <p>Postal Code:- ${e.postalCode}</p>
        <div>
        <button onclick="deleteAddress(${e.addressId})">DELETE</button></div>
    </div>`
    })
    document.getElementById("listAddress").innerHTML=data;
}

//function to delete Address..
function deleteAddress(id){
    del()
    async function del(){
        let options = {
                method: "DELETE",
                headers: {
                        "Content-type": "application/json"
                }
        }
        let p = await fetch('http://localhost:8880/user/address/'+id, options);
        let res = await p.json()
        console.log(res);
        if(res.message!=undefined){
            alert(res.message);
        }else{
            alert("Address Deleted Successfully");
            location.reload();
        }
    };
}









function show(data){

    document.getElementById("resultProducts").innerHTML="";
    let prod=``;
    showBrands(data);
    data.forEach(e => {
        let img="https://icon2.cleanpng.com/20180605/ijl/kisspng-computer-icons-image-file-formats-no-image-5b16ff0d2414b5.0787389815282337411478.jpg";
        if(e.imgs.length>0){
            img=e.imgs[0].link;
        }
        prod += `<div><div>
        <img src="${img}" alt="">
    </div>
    <p>${e.category.name}</p>
    <h3>${e.name}</h3>
    <p>$${e.sellPrice}</p>
    <button onclick="addToCart(${e.productId})">Add to Cart</button>
    </div>`
    });
    document.getElementById("resultProducts").innerHTML=prod;

}
function showCategory(data){
    document.getElementById("listCategory").innerHTML="";
    let cat=``;
    data.forEach(e=>{
        cat += `<div onclick="filterByCategory('${e.categoryId}')">
        <p>&#8667;${e.name}</p></div>`
    });
    document.getElementById("listCategory").innerHTML=cat;
}
function showBrands(data){
    let arr=[];
    let i=0;
    data.forEach(e=>{
        arr[i]=e.brand;
        i++;
    });
    let unique=[...new Set(arr)];
    console.log(unique);
    document.getElementById("listBrand").innerHTML="";
    let brand=``;
    unique.forEach(e=>{
        brand+=`<div onclick="filterByBrands('${e}')">
        <p>&#8667;${e}</p></div>`;
    })
    document.getElementById("listBrand").innerHTML=brand;
}
async function priceLessThan(){
    let value=document.getElementById("filterByPrice").value;
    let link="http://localhost:8880/user/product/price/"+value;
    try{
        let res=await fetch(link,{
            method:'GET',
            headers:{
                "Content-Type":"application/json",
            }
        });
        let data=await res.json();
        // console.log(data);
        if(data!=undefined){
            show(data);
            
        }
        
    }catch(err){
        console.log(err);
    }
    console.log(str);
}
async function filterByBrands(s){
    let str=s.trim();
    let link="http://localhost:8880/user/product/byBrand/"+str;
    try{
        let res=await fetch(link,{
            method:'GET',
            headers:{
                "Content-Type":"application/json",
            }
        });
        let data=await res.json();
        // console.log(data);
        if(data!=undefined){
            show(data);
            
        }
        
    }catch(err){
        console.log(err);
    }
    console.log(str);
}
async function filterByCategory(s){
    let str=s.trim();
    let link="http://localhost:8880/user/product/bycategory/"+str;
    try{
        let res=await fetch(link,{
            method:'GET',
            headers:{
                "Content-Type":"application/json",
            }
        });
        let data=await res.json();
        // console.log(data);
        if(data!=undefined){
            show(data);
            
        }
        
    }catch(err){
        console.log(err);
    }
    console.log(str);
}
async function searchProducts(){
    let s=document.getElementById("searchInput").value;
    let link="http://localhost:8880/user/product/"+s;
    try{
        let res=await fetch(link,{
            method:'GET',
            headers:{
                "Content-Type":"application/json",
            }
        });
        let data=await res.json();
        // console.log(data);
        if(data!=undefined){
            show(data);
        }
        
    }catch(err){
        console.log(err);
    }
};
async function getAllCategories(){
    try{
        let res=await fetch("http://localhost:8880/user/category",{
            method:'GET',
            headers:{
                "Content-Type":"application/json",
            }
        });
        let data=await res.json();
        // console.log(data);
        if(data!=undefined){
            showCategory(data);
        }
    }catch(err){
        console.log(err);
    }
}
async function getAllProducts(){
    try{
        let res=await fetch("http://localhost:8880/user/product",{
            method:'GET',
            headers:{
                "Content-Type":"application/json",
            }
        });
        let data=await res.json();
        // console.log(data);
        if(data!=undefined){
            show(data);
        }
    }catch(err){
        console.log(err);
    }
};
let id=null;
function debounce(func,delay){
    if(id){
        clearTimeout(id);
    }
    id=setTimeout(function(){
        func();
    },delay)
}
