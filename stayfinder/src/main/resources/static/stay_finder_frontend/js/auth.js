const API = "http://localhost:8080";

function register() {

fetch(API+"/auth/register", {
method:"POST",
headers:{"Content-Type":"application/json"},
body:JSON.stringify({
name: document.getElementById("name").value,
email: document.getElementById("email").value,
password: document.getElementById("password").value,
phone: document.getElementById("phone").value,
role: document.getElementById("role").value
})
})
.then(res => res.json())
.then(data => {

    if(data.message){
        alert(data.message);   // 🔴 shows "Email already exists"
        return;
    }

    alert("Registered Successfully ✅");
    window.location="login.html";

})
.catch(err=>{
    console.log(err);
    alert("Registration Failed");
});
}

function login() {

fetch(API+"/auth/login", {
method:"POST",
headers:{"Content-Type":"application/json"},
body:JSON.stringify({
email: email.value,
password: password.value
})
}).then(r=>r.json())
.then(data=>{

console.log(data);   // 🔥 add this to confirm

if(data.token){
    localStorage.setItem("token", data.token);
    localStorage.setItem("role", data.role);
	localStorage.setItem("name",data.name);

    if(data.role === "OWNER"){
        location="owner.html";
    }else{
        location="home.html";
    }
}else{
    alert("Invalid email or password");
}
})



}
