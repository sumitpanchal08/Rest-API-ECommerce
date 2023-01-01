async function signup(){
    let d={
        'firstName': document.getElementById("name").value,
        'mobile': document.getElementById("mobile").value,
        'email': document.getElementById("email").value,
        'password': document.getElementById("password").value,
        'dob': document.getElementById("dob").value
    }
    // console.log(data);
    try{
        let res=await fetch("http://localhost:8880/user/register",{
            method:"POST",
            headers:{
                "Content-Type":"application/json"
            },
            body: JSON.stringify(d)
        });
        let data=await res.json();
        if(data.message!=undefined){
            alert(data.message);
        }else{
            alert("Signup SuccessFull, Now you can Login!!");
            window.location.href="index.html";
        }
        console.log(data);
    }catch(err){
        console.log(err);
    }
}