const API="https://stayfinder-backend-2-uuo6.onrender.com";

function showOwnerSection(type){

document.getElementById("addHostelSection").style.display="none";
document.getElementById("myHostelsSection").style.display="none";
document.getElementById("addRoomSection").style.display="none";
document.getElementById("myRoomsSection").style.display="none";

if(type==="addHostel")
document.getElementById("addHostelSection").style.display="block";

if(type==="myHostels"){
document.getElementById("myHostelsSection").style.display="block";
loadMyHostels();
}

if(type==="addRoom")
document.getElementById("addRoomSection").style.display="block";

if(type==="myRooms"){
document.getElementById("myRoomsSection").style.display="block";
loadMyRooms();
}
}

// LOGOUT
function logout(){
localStorage.clear();
window.location="login.html";
}


// ---------------- ADD HOSTEL ----------------
function addHostel(){

let formData = new FormData();

formData.append("hostelName", document.getElementById("hostelName").value);
formData.append("gender", document.getElementById("gender").value);
formData.append("rent", document.getElementById("rent").value);
formData.append("sharingType", document.getElementById("sharingType").value);
formData.append("contact", document.getElementById("contact").value);
formData.append("location", document.getElementById("location").value);
formData.append("details", document.getElementById("hostelDetails").value);

formData.append("wifi", document.getElementById("wifi").checked);
formData.append("ac", document.getElementById("ac").checked);
formData.append("mess", document.getElementById("mess").checked);
formData.append("laundry", document.getElementById("laundry").checked);
formData.append("hotWater", document.getElementById("hotWater").checked);

let file = document.getElementById("file");

if(file.files.length>0){
formData.append("file", file.files[0]);
}

fetch(API+"/hostel/add",{
method:"POST",
headers:{
"Authorization":"Bearer "+localStorage.getItem("token")
},
body:formData
})
.then(res=>res.json())
.then(()=>{
alert("Hostel Added Successfully ✅");
});
}


// ---------------- LOAD MY HOSTELS ----------------
function loadMyHostels(){

fetch(API+"/hostel/my",{
headers:{
"Authorization":"Bearer "+localStorage.getItem("token")
}
})
.then(r=>r.json())
.then(data=>{

let html="";

data.forEach(h=>{

html+=`
<tr>

<td>
${h.imageUrl 
? `<img src="http://localhost:8080/${h.imageUrl}" width="80">`
: `No Image`}
</td>

<td>${h.hostelName}</td>
<td>₹${h.rent}</td>
<td>${h.gender}</td>
<td>${h.sharingType}</td>
<td>${h.wifi ? "Yes" : "No"}</td>
<td>${h.ac ? "Yes" : "No"}</td>
<td>${h.laundry ? "Yes" : "No"}</td>
<td>${h.mess ? "Yes" : "No"}</td>
<td>${h.hotWater ? "Yes" : "No"}</td>
<td>${h.location ?? "-"}</td>

<td>${h.details ?? "-"}</td>
<td id="owner-rating-${h.id}">⭐ Loading...</td>

<td>
<button onclick="viewOwnerReviews(${h.id})">View</button>
</td>

<td>
<button class="delete-btn" onclick="deleteHostel(${h.id})">Delete</button>
</td>
</tr>
`;

});

document.getElementById("myHostelsList").innerHTML=html;
loadOwnerRatings(data);
});
}
function viewOwnerReviews(id){

fetch(API+"/review/hostel/"+id)
.then(r=>r.json())
.then(data=>{

if(!Array.isArray(data)){
alert("No reviews yet");
return;
}

let html="Student Reviews:\n\n";

data.forEach(r=>{
html+= r.studentName+" ⭐"+r.rating+"\n"+r.comment+"\n\n";
});

alert(html);

});
}
function loadOwnerRatings(hostels){

hostels.forEach(h=>{

if(!h.id) return;

fetch(API+"/review/avg/"+h.id)
.then(r=>r.text())
.then(avg=>{
document.getElementById("owner-rating-"+h.id).innerText =
"⭐ "+parseFloat(avg).toFixed(1);
})
.catch(()=>{

document.getElementById("owner-rating-"+h.id).innerText =
"⭐ 0";

});

});
}

// ---------------- ADD ROOM ----------------
function addRoom(){

let formData = new FormData();

formData.append("roomType", document.getElementById("roomType").value);
formData.append("rent", document.getElementById("roomRent").value);
formData.append("sharing", document.getElementById("roomSharing").value);
formData.append("location", document.getElementById("roomLocation").value);
formData.append("contact", document.getElementById("roomContact").value);
formData.append("details", document.getElementById("roomDetails").value);
formData.append("parking", document.getElementById("roomParking").checked);

let file = document.getElementById("roomImage");

if(file.files.length>0){
formData.append("file", file.files[0]);
}

fetch(API+"/room/add",{
method:"POST",
headers:{
"Authorization":"Bearer "+localStorage.getItem("token")
},
body:formData
})
.then(res=>res.json())
.then(()=>{
alert("Room Added Successfully ✅");
});
}


// ---------------- LOAD MY ROOMS ----------------
function loadMyRooms(){

fetch(API+"/room/my",{
headers:{
"Authorization":"Bearer "+localStorage.getItem("token")
}
})
.then(r=>r.json())
.then(data=>{

let html="";

data.forEach(r=>{

html+=`
<tr>
<td>${r.roomType}</td>
<td>₹${r.rent}</td>
<td>
${r.imageUrl 
? `<img src="http://localhost:8080/${r.imageUrl}" width="80">`
: `No Image`}
</td>
<td>${r.sharing ?? "-"}</td>
<td>${r.parking ? "Yes" : "No"}</td>
<td>${r.location ?? "-"}</td>
<td>
<td>${r.details ?? "-"}</td>

<td>
<button onclick="deleteRoom(${r.id})">Delete</button>
</td>
</tr>
`;

});

document.getElementById("myRoomsList").innerHTML=html;

});
}

function deleteHostel(id){

fetch(API+"/hostel/delete/"+id,{
method:"DELETE",
headers:{
"Authorization":"Bearer "+localStorage.getItem("token")
}
})
.then(()=>{
alert("Hostel Deleted");
loadMyHostels();
});
}

function deleteRoom(id){

fetch(API+"/room/delete/"+id,{
method:"DELETE",
headers:{
"Authorization":"Bearer "+localStorage.getItem("token")
}
})
.then(()=>{
alert("Room Deleted");
loadMyRooms();
});
}