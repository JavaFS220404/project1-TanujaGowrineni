const url = "http://localhost:8080/ERS/core/"

let btnRegister = document.getElementById("register");
let btnLogout = document.getElementById("logout");


btnRegister.addEventListener("click", createUser);
btnLogout.addEventListener("click", logout);

async function logout(){
  console.log("before logout");
  let response = await fetch(url+"logout", {
    credentials:"include"
  });
  console.log("after logout");
}


async function createUser(){

  document.getElementById("errormessage").innerText = "";
  let newUser = userInfo(); 
  let response = await fetch(url+"register", {
    method:"POST",
    body:JSON.stringify(newUser),
    credentials:"include"
  });
  console.log("after fetch");
  if(response.status === 201){
    document.getElementById("errormessage").innerText = "User Registration is successful. Click on Login button.";
    let data = await response.json();
  }else{
    document.getElementById("errormessage").innerText = "User Registration is unsuccessful. Please try again.";
  }

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
