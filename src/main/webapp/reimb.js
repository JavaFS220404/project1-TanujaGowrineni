const url = "http://localhost:8080/ERS/core/"

let btnSubmit = document.getElementById("submit");

btnSubmit.addEventListener("click", createReimb);

async function createReimb(){

  document.getElementById("errormessage").innerText = "";
  let newReimb = reimbInfo(); 
  let response = await fetch(url+"reimb", {
    method:"POST",
    body:JSON.stringify(newReimb),
    credentials:"include"
  });
  if(response.status === 201){
    document.getElementById("errormessage").innerText = "Reimbusement added successful.";
    let data = await response.json();
    document.getElementById("errormessage").innerText = data;
    populateReimbTable(data);
  }else{
    document.getElementById("errormessage").innerText = "Could not add Reimbusement. Please try again.";
  }

}

function reimbInfo(){

    let reimbtype = document.getElementById("reimbtype").value;
    let amount = document.getElementById("amount").value;
    let description = document.getElementById("description").value;
    //let receiptimag = document.getElementById("recipt").value;
    
    let reimb = {
      id:0,
      status: "PENDING",
      author: null,
      resolver: null,
      amount: amount,
      description: description,
      creationDate: null,
      resolutionDate: null,
    //  receiptImage: receiptimag,
      type: reimbtype
    }
  
    return reimb;
  }


  async function getReimbList(){
    let response = await fetch(url+"reimb", {
      credentials:"include"
    });
  
    if(response.status===200){
      let list = await response.json();
  
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
      let author = document.createElement("tr");
      let resolvedon = document.createElement("td");
      let resolver = document.createElement("td");
      let status = document.createElement("td");
  
      id.innerText = reimb.id;
      amount.innerText = reimb.amount;
      type.innerText = reimb.type;
      description.innerText = reimb.description;
      createdon.innerText = reimb.creationDate;
      author.innerText = reimb.author.firstName+" "+" "+reimb.author.lastName;
      resolvedon.innerText = reimb.resolutionDate;
      resolver.innerText = reimb.resolver.firstName+" "+todo.resolver.lastName;
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


  async function getReimbList(){

    let status = "PENDING";
    let response = await fetch(url+"reimb", {
    method:"POST",
    body:JSON.stringify(newReimb),
    credentials:"include"
  });
  
    if(response.status===200){
      let list = await response.json();
  
      populateReimbTable(list);
    }
  }

  document.getElementById("errormessage").innerText = "";
  let newReimb = reimbInfo(); 
  let response = await fetch(url+"reimb", {
    method:"POST",
    body:JSON.stringify(newReimb),
    credentials:"include"
  });
  if(response.status === 201){
    document.getElementById("errormessage").innerText = "Reimbusement added successful.";
    let data = await response.json();
    document.getElementById("errormessage").innerText = data;
    populateReimbTable(data);
  }else{
    document.getElementById("errormessage").innerText = "Could not add Reimbusement. Please try again.";
  }