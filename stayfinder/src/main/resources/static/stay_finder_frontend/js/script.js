const API = "http://localhost:8080";

// REGISTER
function register() {

fetch(API + "/auth/register", {
method: "POST",
headers: { "Content-Type": "application/json" },
body: JSON.stringify({
name: document.getElementById("name").value,
email: document.getElementById("email").value,
password: document.getElementById("password").value,
phone: document.getElementById("phone").value,
role: document.getElementById("role").value
})
})
.then(res => res.json())
.then(data => {
alert("Registered!");
window.location = "login.html";
});
}

// LOGIN
function login() {

fetch(API + "/auth/login", {
method: "POST",
headers: { "Content-Type": "application/json" },
body: JSON.stringify({
email: document.getElementById("email").value,
password: document.getElementById("password").value
})
})
.then(res => res.json())
.then(data => {
localStorage.setItem("token", data.token);
localStorage.setItem("role", data.role);
window.location = "dashboard.html";
});

}

window.onload = function () {

let role = localStorage.getItem("role");

if(role !== "OWNER") {
document.getElementById("ownerSection").style.display = "none";
}

loadHostels();
}

// ADD HOSTEL
function addHostel() {

fetch(API + "/hostel/add", {
method: "POST",
headers: {
"Content-Type": "application/json",
"Authorization": "Bearer " + localStorage.getItem("token")
},
body: JSON.stringify({
hostelName: document.getElementById("hostelName").value,
gender: document.getElementById("gender").value,
rent: document.getElementById("rent").value
})
})
.then(res => res.json())
.then(data => {
alert("Hostel Added!");
loadHostels();
});
}

// LOAD HOSTELS
function loadHostels() {

fetch(API + "/hostel/all")
.then(res => res.json())
.then(data => {

let html = "";

data.forEach(h => {
html += `
<div class="card">
<h3>${h.hostelName}</h3>
<p>₹${h.rent}</p>
<p>${h.location}</p>
</div>
`;
});


document.getElementById("hostels").innerHTML = html;

});
}

loadHostels();
