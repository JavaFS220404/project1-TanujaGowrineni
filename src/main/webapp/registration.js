const url = "http://localhost:8080/ERS/core/"

let btnRegister = document.getElementById("register");
btnRegister.addEventListener("click", createUser);

async function logout(){
  console.log("before logout");
  let response = await fetch(url+"logout", {
    credentials:"include"
  });
  console.log("after logout");
}


async function createUser(){

  if(document.getElementById("username").value == ""){
    document.getElementById("errormessage").innerText = "Please enter Username.";
    document.getElementById("username").focus();
  }else if (document.getElementById("password").value == ""){
  document.getElementById("errormessage").innerText = "Please enter Password.";
  document.getElementById("password").focus();
  }else if (document.getElementById("email").value == ""){
    document.getElementById("errormessage").innerText = "Please enter Email.";
    document.getElementById("email").focus();
  }else{

    let newUser = userInfo(); 
    let response = await fetch(url+"register", {
      method:"POST",
      body:JSON.stringify(newUser),
      credentials:"include"
    });
    console.log("after fetch");
    //document.getElementById("errormessage").innerText = ""; 
    if(response.status === 201){
      clearfields();
      document.getElementById("errormessage").value = "User Registration is successful.";
      let data = await response.json();
    }else{
      document.getElementById("errormessage").value = "User Registration is unsuccessful.";
    }
  }

}


function clearfields(){
  document.getElementById("errormessage").value = "";
  document.getElementById("username").value = "";
  document.getElementById("password").value = "";
  document.getElementById("userrole").value = "EMPLOYEE";
  document.getElementById("firstname").value = "";
  document.getElementById("lastname").value = "";
  document.getElementById("email").value = "";
}

function userInfo(){

    let newUsername = document.getElementById("username").value;
    let newPassword = document.getElementById("password").value;
    let userRole = document.getElementById("userrole").value;
    let userFirstname = document.getElementById("firstname").value;
    let userLastname = document.getElementById("lastname").value;
    let userEmail = document.getElementById("email").value;
    
    let user = {
      id:0,
      username: newUsername,
      password: newPassword,
      role: userRole,
      firstname: userFirstname,
      lastname: userLastname,
      email: userEmail
    }
  
    return user;
  }
