
function goHome(){
    document.getElementById("home").style.display="block";
    document.getElementById("products").style.display="none";
}
function goProduct(){
    document.getElementById("home").style.display="none";
    document.getElementById("products").style.display="flex";
    getAllProducts();
}










function show(data){
    document.getElementById("resultProducts").innerHTML="";
    let prod=``;

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
    </div>`
    });
    document.getElementById("resultProducts").innerHTML=prod;

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
        console.log(data);
        if(data!=undefined){
            show(data);
        }
        
    }catch(err){
        console.log(err);
    }
};
async function getAllProducts(){
    try{
        let res=await fetch("http://localhost:8880/user/product",{
            method:'GET',
            headers:{
                "Content-Type":"application/json",
            }
        });
        let data=await res.json();
        console.log(data);
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