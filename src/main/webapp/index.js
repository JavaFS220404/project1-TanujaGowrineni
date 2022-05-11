const url = "http://localhost:8080/ERS/core/"

let loginbtn = document.getElementById("btnLogin");
let registerbtn = document.getElementById("btnRegister");
let getbtn = document.getElementById("btnGetReimb");

loginbtn.addEventListener("click", login);
registerbtn.addEventListener("click", register);
getbtn.addEventListener("click", getReimbList);

async function login(){

  document.getElementById("errormessage").innerText ="";
  let uName = document.getElementById("username").value;
  let pWord = document.getElementById("password").value; 

  let user = {
    username:uName,
    password:pWord
  };

  let response = await fetch(url+"login", {
    method:"POST",
    body: JSON.stringify(user),
    credentials:"include"
  });
  
  if(response.status===200){
    console.log("Login successful");
    console.log(response);
  }else{
    document.getElementById("errormessage").innerText = "Username/Password is incorrect! Please try again.";
    console.log("Could not log in");
    console.log(response);
  }
}

function register(){

  console.log("register button click");
  window.location.href = "http://localhost:8080/ERS/registration.html";
  

}


async function getReimbList(){

  let status = {
    statustype: document.getElementById("status").value
  }
   
  let response = await fetch(url+"reimb", {
    method:"POST",
    body:JSON.stringify(status),
    credentials:"include"
  });

  if(response.status===200){
    let list = await response.json();
    console.log(list);
    populateReimbTable(list);
  }
}


function populateReimbTable(list){
  let tableBody = document.getElementById("reimbListBody");
  tableBody.innerHTML="";
  for(let reimb of list){
    let row = document.createElement("tr");
    let id = document.createElement("td");
    let amount = document.createElement("td");
    let type = document.createElement("td");
    let description = document.createElement("td");
    let createdon= document.createElement("td");
    let author = document.createElement("td");
    let resolvedon = document.createElement("td");
    let resolver = document.createElement("td");
    let status = document.createElement("td");

    id.innerText = reimb.id;
    amount.innerText = reimb.amount;
    type.innerText = reimb.type;
    description.innerText = reimb.description;

    var timestamp = reimb.creationDate;
    var date = new Date(timestamp);
    createdon.innerText = date.getDate()+"/"+(date.getMonth()+1)+"/"+date.getFullYear()+" "+date.getHours()+":"+date.getMinutes()+ ":"+date.getSeconds();
    
    author.innerText = reimb.author.firstname+" "+reimb.author.lastname;
    
    if(reimb.resolutionDate != null){
      var timestamp = reimb.resolutionDate;
      var date = new Date(timestamp);
      resolvedon.innerText = date.getDate()+"/"+(date.getMonth()+1)+"/"+date.getFullYear();
    }else 
      resolvedon.innerText ="";
      
    if (reimb.resolver != null)
       resolver.innerText = reimb.resolver.firstname+" "+reimb.resolver.lastname;
    else
      resolver.innerText = "";

    status.innerText = reimb.status;

    row.appendChild(id);
    row.appendChild(amount);
    row.appendChild(type);
    row.appendChild(description);
    row.appendChild(createdon);
    row.appendChild(author);
    row.appendChild(resolvedon);
    row.appendChild(resolver);
    row.appendChild(status);
    tableBody.appendChild(row);
  }
}

