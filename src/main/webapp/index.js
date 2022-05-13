const url = "http://localhost:8080/ERS/core/"

let loginbtn = document.getElementById("btnLogin");
let registerbtn = document.getElementById("btnRegister");
let getbtn = document.getElementById("btnGetReimb");
let updatebtn = document.getElementById("btnUpdate");
let btnSubmit = document.getElementById("submit");
let btnLogout = document.getElementById("logout");



loginbtn.addEventListener("click", login);
registerbtn.addEventListener("click", register);
getbtn.addEventListener("click", getReimbList);
updatebtn.addEventListener("click", updateReimb);
btnSubmit.addEventListener("click", createReimb);
btnLogout.addEventListener("click", logout);


function logout(){
  console.log("hi");
  window.location.href = "http://localhost:8080/ERS/index.html";

}

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
     let user = await response.json();
     document.getElementById("login").hidden = true;
     document.getElementById('welcome').innerText = "Welcome "+user.firstname+" "+user.lastname;
     document.getElementById("logout").hidden = false;
     console.log("Login successful");
     console.log(response);
    
    revealdivs(user);

  }else{
    document.getElementById('errormessage').innerText = "Username/Password is incorrect! Please try again.";
    console.log("Could not log in");
    console.log(response);
  }
}

function register(){

  console.log("register button click");
  window.location.href = "registration.html";

}


function revealdivs(user){
  console.log(user.role);
  if(user.role == "EMPLOYEE"){
    document.getElementById("employee").hidden = false;

  }else{
    document.getElementById("manager").hidden = false;
    document.getElementById("reimbtable").hidden = true;
    document.getElementById("updatectl").hidden = true;
  }
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

 // document.getElementById("reimbtable").hidden = true;
  //document.getElementById("updatectl").hidden = true;
  //document.getElementById("displayreimtable").hidden = true;
  if(response.status===200){
    let list = await response.json();
    console.log(list);
    populateReimbTable(list,"reimbListBody");
    document.getElementById("reimbtable").hidden = false;
  
    
    if (document.getElementById("status").value == "PENDING"){
      document.getElementById("updatectl").hidden = false;
    }else{
      document.getElementById("updatectl").hidden = true;
    }
      
    if ( document.getElementById("updatectl").hidden == false){
      document.getElementById("displayreimtable").hidden = false;
    }else{
      document.getElementById("displayreimtable").hidden = true;
    }

  }
}


function populateReimbTable(list, tablename){

  let tableBody = document.getElementById(tablename);
  tableBody.innerHTML="";
    if( list != null){
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
   }else{

    let row = document.createElement("tr");
    tableBody.appendChild(row);
    let msg = document.createElement("td");
    msg.innerText = "No Records available";
    row.appendChild(msg);
    tableBody.appendChild(row);

  }
}

async function updateReimb(){

  if (document.getElementById("reimbId").value != ""){

    document.getElementById("displayreimtable").hidden = true;

    let reimbtobeupdated = {
        id:document.getElementById("reimbId").value,
        status: document.getElementById("reimbStatus").value,
        author: null,
        resolver: null,
        amount: null,
        description: null,
        creationDate: null,
        resolutionDate: null,
        type: null
      }

    let response = await fetch(url+"reimb", {
      method:"PUT",
      body:JSON.stringify(reimbtobeupdated),
      credentials:"include"
    });

    if(response.status===200){
      getReimbList();
      document.getElementById("displayreimtable").hidden = false;
      let data = await response.json();
      showReimbursement(data);
      document.getElementById("reimbtable").hidden=false;
      document.getElementById("reimbId").value = "";
    // document.getElementById("errormessage").innerText = "Reimbusement Status updated successfully.";
    }else{
      console.log("Could not update Reimbusement");
      console.log(response);
      //document.getElementById("errormessage").innerText = "Could not update Reimbusement Status. Please try again.";
    }
  }
}

// Add Reimbursement
async function createReimb(){
  let newReimb = reimbInfo(); 
  let response = await fetch(url+"reimb", {
    method:"POST",
    body:JSON.stringify(newReimb),
    credentials:"include"
  });
  if(response.status === 201){
   // document.getElementById("errormessage").innerText = "Reimbusement added successful.";
    let data = await response.json();
    console.log(data);
    console.log("Reimbursement added.");
    document.getElementById("amount").value = "";
    document.getElementById("description").value = "";
    document.getElementById("reimbtype").value = "LODGING";
    populateReimbTable(data,"reimBody");
    document.getElementById("displayreimtable").hidden = false;
  }else{
    document.getElementById("errormessage").innerText = "Could not add Reimbusement. Please try again.";
  }
}

function showReimbursement(reimb){

    let tableBody = document.getElementById("reimBody");
    tableBody.innerHTML="";
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
  
    document.getElementById("displayreimtable").hidden=false;
}


function reimbInfo(){

  let reimbtype = document.getElementById("reimbtype").value;
  let amount = document.getElementById("amount").value;
  let description = document.getElementById("description").value;
  
  let reimb = {
    id:0,
    status: "PENDING",
    author: null,
    resolver: null,
    amount: amount,
    description: description,
    creationDate: null,
    resolutionDate: null,
    type: reimbtype
  }

  return reimb;
}
