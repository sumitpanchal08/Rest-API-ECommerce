// let loginAccounts=JSON.parse(localStorage.getItem("loginAccounts")) || [];

document.getElementById("backToHome").addEventListener("click",function(){
    window.location.href="index.html";
})
document.getElementById("backToSign").addEventListener("click",function(){
    let main=document.getElementById("main");
    let login=document.getElementById("login");
    main.style.display="block";
    login.style.display="none";
})
document.getElementById("goToLoginPage").addEventListener("click",function(){
    let main=document.getElementById("main");
    let login=document.getElementById("login");
    main.style.display="none";
    login.style.display="block";
})
document.getElementById("goToSignup").addEventListener("click",function(){
    window.location.href="signup.html";
})




async function doLogin(phone,pass){
    try{
        let res=await fetch("http://localhost:8880/user/login",{
            method:'POST',
            headers:{
                "Content-Type":"application/json"
            },
            body: JSON.stringify({
                'mobile':phone.value,
                'password':pass.value
            })
        });
            let data=await res.json();
            if(data.message!=undefined){
                alert(data.message);
            }else{
                console.log(data);
                let token=data.uuid;
                localStorage.setItem("token",token);
                alert("Login Successfull!!!");
                window.location.href="index.html";
            }
    }catch(err){
        // 
        // localStorage.setItem("token",token);
        // alert("Login Successfull!!!");
        // window.location.href="index.html";
        console.log(err);
    }
}

document.getElementById("submitLogin").addEventListener("click",function(){
    let phone=document.getElementById("loginId");
    let pass=document.getElementById("loginPass");
    if(phone.value=="" || pass.value=="" ){
        alert("Fill all the Inputs!!");
    }
    let uuid=doLogin(phone,pass);
})