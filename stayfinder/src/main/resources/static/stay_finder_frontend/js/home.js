const API = "http://localhost:8080";

// ---------------- SHOW SECTIONS ----------------

function showSection(type){

document.getElementById("hostelSection").style.display="none";
document.getElementById("roomSection").style.display="none";
document.getElementById("roommateSection").style.display="none";

if(type==="hostel")
document.getElementById("hostelSection").style.display="block";

if(type==="room")
document.getElementById("roomSection").style.display="block";

if(type==="roommate")
document.getElementById("roommateSection").style.display="block";
document.getElementById("postRoommateSection").style.display="none";

if(type==="postRoommate")
document.getElementById("postRoommateSection").style.display="block";

if(type==="myPosts"){
document.getElementById("myPostsSection").style.display="block";
loadMyPosts(); // ⭐ THIS WAS MISSING
}
}

// ---------------- HOSTEL SEARCH ----------------

function searchHostels(){

fetch(API+"/hostel/all")
.then(r=>r.json())
.then(data=>{

let filtered = data.filter(h => {

return (!hLocation.value || h.location.toLowerCase().includes(hLocation.value.toLowerCase()))
&& (!hGender.value || h.gender === hGender.value)
&& (!hRent.value || h.rent <= hRent.value)
&& (!hWifi.checked || h.wifi === true)
&& (!hMess.checked || h.mess === true)

});

showHostels(filtered);

});
}

// ---------------- ROOM SEARCH ----------------

function searchRooms(){

fetch(API+"/room/all")
.then(r=>r.json())
.then(data=>{

let filtered = data.filter(r => {

return (!rLocation.value || r.location.toLowerCase().includes(rLocation.value.toLowerCase()))
&& (!rType.value || r.roomType === rType.value)
&& (!rRent.value || r.rent <= rRent.value)
&& (!rParking.checked || r.parking === true)

});

showRooms(filtered);

});
}

// ---------------- ROOMMATE SEARCH ----------------

function searchRoommates(){

fetch(API+"/roommate/all")
.then(r=>r.json())
.then(data=>{

let filtered = data.filter(r => {

return (!rmLocation.value || r.location.toLowerCase().includes(rmLocation.value.toLowerCase()))
&& (!rmGender.value || r.genderPreference === rmGender.value)
&& (!rmRent.value || r.rent <= rmRent.value)

});

showRoommates(filtered);

});
}

// ---------------- DISPLAY ----------------

function showRoommates(data){

let html = `
<table class="result-table">
<tr>
<th>Image</th>
<th>Description</th>
<th>Rent</th>
<th>Preference</th>
<th>Details</th>
<th>Location</th>
<th>Owner</th>
<th>Contact</th>
</tr>
`;

data.forEach(r=>{

html += `
<tr>

<td>
${r.imageUrl ? `<img src="http://localhost:8080/${r.imageUrl}" width="80">` : "No Image"}
</td>

<td>${r.description}</td>
<td>₹${r.rent}</td>
<td>${r.genderPreference}</td>
<td>${r.details || "-"}</td>
<td>${r.location}</td>
<td>${r.ownerName || "N/A"}</td>
<td>
<button onclick="callOwner('${r.ownerPhone}')">Call</button>
<button onclick="whatsappOwner('${r.ownerPhone}')">WhatsApp</button>
</td>

</tr>
`;

});

html += `</table>`;
results.innerHTML = html;
}




function showRooms(data){

let html = `
<table class="result-table">
<tr>
<th>Type</th>
<th>Rent</th>
<th>Location</th>
<th>Image</th>
<th>Parking</th>
<th>Details</th>
<th>Owner</th>
<th>Contact</th>
</tr>
`;

data.forEach(r=>{
html += `
<tr>
<td>${r.roomType}</td>
<td>₹${r.rent}</td>
<td>${r.location}</td>
<td>${r.imageUrl ? `<img src="http://localhost:8080/${r.imageUrl}" width="80">` : "No Image"}</td>
<td>${r.parking ? "Yes" : "No"}</td>
<td>${r.details || "-"}</td>
<td>${r.ownerName || "N/A"}</td>
<td>
<button onclick="callOwner('${r.ownerPhone}')">Call</button>
<button onclick="whatsappOwner('${r.ownerPhone}')">WhatsApp</button>
</td>
</tr>
`;
});

html += `</table>`;
results.innerHTML = html;
}


function showHostels(data){

let html = `
<table class="result-table">
<tr>
<th>Name</th>
<th>Rent</th>
<th>Type</th>
<th>Location</th>
<th>Sharing</th>
<th>WiFi</th>
<th>AC</th>
<th>Laundry</th>
<th>Mess</th>
<th>Details</th>
<th>Image</th>
<th>Owner</th>
<th>Contact</th>
<th>Rating</th>
<th>Reviews</th>
<th>Add Review</th>
</tr>
`;

data.forEach(h=>{
html += `
<tr>
<td>${h.hostelName}</td>
<td>₹${h.rent}</td>
<td>${h.gender}</td>
<td>${h.location}</td>
<td>${h.sharingType}</td>
<td>${h.wifi ? "Yes" : "No"}</td>
<td>${h.ac ? "Yes" : "No"}</td>
<td>${h.laundry ? "Yes" : "No"}</td>
<td>${h.mess ? "Yes" : "No"}</td>
<td>${h.details || "-"}</td>
<td>
    <img src="http://localhost:8080/${h.imageUrl}" width="120">
</td>
<td>${h.ownerName || "N/A"}</td>
<td id="rating-${h.id}">⭐ Loading...</td>

<td>
<button onclick="viewReviews(${h.id})">View</button>
</td>

<td>
<select id="rate-${h.id}">
<option>1</option>
<option>2</option>
<option>3</option>
<option>4</option>
<option>5</option>
</select>

<input id="comment-${h.id}" placeholder="Comment">

<button onclick="addReview(${h.id})">Submit</button>
</td>
<td>
<button  onclick="whatsappOwner('${h.ownerPhone}')">WhatsApp</button>
<button onclick="callOwner('${h.ownerPhone}')">Call</button>
</td>
</tr>
`;
});

html += `</table>`;
results.innerHTML = html;
loadRatings(data);
}


function postRoommate(){

let token = localStorage.getItem("token");

if(!token){
alert("Please login again");
return;
}

// clear old errors
descError.innerText="";
rentError.innerText="";
sharingError.innerText="";
genderError.innerText="";
locationError.innerText="";
contactError.innerText="";

let isValid = true;

// FIELD VALIDATION
if(!rmDesc.value){
descError.innerText="Enter room description";
isValid=false;
}

if(!postRent.value){
rentError.innerText="Enter rent amount";
isValid=false;
}

if(!rmSharing.value){
sharingError.innerText="Enter sharing count";
isValid=false;
}

if(!rmGenderPref.value){
genderError.innerText="Select gender preference";
isValid=false;
}

if(!postLocation.value){
locationError.innerText="Enter location";
isValid=false;
}

if(!postContact.value){
contactError.innerText="Enter contact number";
isValid=false;
}

if(!isValid) return;

// SEND DATA
let formData = new FormData();

// image optional
if(postImage.files.length > 0){
formData.append("file", postImage.files[0]);
}

formData.append("description", rmDesc.value);
formData.append("rent", postRent.value);
formData.append("sharing", rmSharing.value);
formData.append("genderPreference", rmGenderPref.value);
formData.append("location", postLocation.value);
formData.append("contact", postContact.value);
formData.append("details", rmDetails.value); // ✅ FIXED

fetch(API+"/roommate/add",{
method:"POST",
headers:{
"Authorization":"Bearer "+token
},
body:formData
})
.then(res => res.json())
.then(()=>{
alert("Posted Successfully ✅");
loadMyPosts();
});
}

function loadMyPosts(){

fetch(API+"/roommate/my",{
headers:{
"Authorization":"Bearer "+localStorage.getItem("token")
}
})
.then(r=>r.json())
.then(data=>{

let html = `
<table class="result-table">
<tr>
<th>Image</th>
<th>Description</th>
<th>Rent</th>
<th>Sharing</th>
<th>Location</th>
<th>Details</th>
<th>Delete</th>
</tr>
`;

data.forEach(p=>{

html += `
<tr>
<td>
${p.imageUrl ? `<img src="http://localhost:8080/${p.imageUrl}" width="120">` : "No Image"}
</td>
<td>${p.description || "-"}</td>
<td>₹${p.rent || "-"}</td>
<td>${p.sharing || "-"}</td>
<td>${p.location || "-"}</td>
<td>${p.details || "-"}</td>
<td>
<button onclick="deleteRoommate(${p.id})">Delete</button>
</td>
</tr>
`;

});

html += `</table>`;

document.getElementById("myPostsList").innerHTML=html;

});
}

function deleteRoommate(id){

fetch(API+"/roommate/delete/"+id,{
method:"DELETE",
headers:{
"Authorization":"Bearer "+localStorage.getItem("token")
}
})
.then(()=>{
alert("Deleted Successfully");
loadMyPosts();
});
}


function addReview(id){

fetch(API+"/review/add?hostelId="+id+
"&rating="+document.getElementById("rate-"+id).value+
"&comment="+document.getElementById("comment-"+id).value,{

method:"POST",
headers:{
"Authorization":"Bearer "+localStorage.getItem("token")
}
})
.then(()=>alert("Review Added ⭐"));

}

function viewReviews(id){

fetch(API+"/review/hostel/"+id)
.then(r=>r.json())
.then(data=>{

let html="Reviews:\n\n";

data.forEach(r=>{
html+= r.studentName+" ⭐"+r.rating+"\n"+r.comment+"\n\n";
});

alert(html);

});
}
function showWelcome(){

let name = localStorage.getItem("name");

if(name){
document.getElementById("welcomeUser").innerText =
"👋 Welcome, " + name;
}

}
function loadRatings(hostels){

hostels.forEach(h=>{

if(!h.id) return;   // ⭐ safety

fetch(API+"/review/avg/"+h.id)
.then(r=>r.text())
.then(avg=>{
document.getElementById("rating-"+h.id).innerText =
"⭐ "+parseFloat(avg).toFixed(1);
});

});
}
function logout(){

// clear login data
localStorage.removeItem("token");
localStorage.removeItem("role");

// redirect to login
window.location = "login.html";
}

// ---------------- DEFAULT ----------------

window.onload = function() {
    showSection('hostel');
}

function callOwner(phone){
window.location.href = "tel:"+phone;
}

function whatsappOwner(phone){
window.open("https://wa.me/91"+phone);
}

window.onload = function() {

let token = localStorage.getItem("token");

if(!token){
alert("Session expired. Please login again.");
window.location="login.html";
return;
}

showSection('hostel');
/*showWelcome();*/

}
