const API = "https://stayfinder-backend-2-uuo6.onrender.com";

function addHostel(){

fetch(API+"/hostel/add",{
method:"POST",
headers:{
"Content-Type":"application/json",
"Authorization":"Bearer "+localStorage.getItem("token")
},
body:JSON.stringify({
hostelName:hostelName.value,
gender:gender.value,
rent:rent.value
})
}).then(r=>r.json())
.then(()=>alert("Added"));
}

function loadHostels(){

fetch(API+"/hostel/all")
.then(r=>r.json())
.then(data=>{

let html="";

data.forEach(h=>{
html+=`
<div class="card">
<h3>${h.hostelName}</h3>
<p>₹${h.rent}</p>
<p>${h.location}</p>
</div>
`;
});

document.getElementById("list").innerHTML=html;

});
}
