function logout(){
localStorage.clear();
location="login.html";
}

function goAddHostel(){
location="add-hostel.html";
}

function goHostels(){
location="hostels.html";
}

window.onload = function(){
if(localStorage.getItem("role")!=="OWNER"){
document.getElementById("ownerSection").style.display="none";
}
}
