
document.addEventListener("DOMContentLoaded", function(event){
    var userType = document.querySelector('#userType');
    var registerForms = document.querySelectorAll(".register-form");
    var doctorRegisterForm = document.querySelector("#doctorRegisterForm .register-button");
    var patientRegisterForm = document.querySelector("#patientRegisterForm .register-button");
    var counsellorRegisterForm = document.querySelector("#counsellorRegisterForm .register-button");
    var skeletonContainer = document.querySelector(".skeleton-container")
    var mobileNum = document.querySelector(".mobileNumber");
    var registerFetchUrl =  "http://localhost:8080/savePatient"; // Replace With API URL

userType.addEventListener('change', function(event){
    var value = event.target.value;
    var findForm = document.querySelector("#"+value+"RegisterForm");
    for (let index = 0; index < registerForms.length; index++) {
        var element = registerForms[index];
        element.reset();
        if(!element.classList.contains("display-none")){
            element.classList.add("display-none");
        }        
        
    }
    if(findForm){    
        skeletonContainer.classList.remove("display-none");
        setTimeout(function(){
            skeletonContainer.classList.add("display-none");
            findForm.classList.remove("display-none");
        },1000);
        
    }
});

// patient form submit event
patientRegisterForm.addEventListener('click', function(event){
    event.preventDefault();
    var form = this.parentElement;
    var firstName = form.querySelector(".firstName");
    var lastName = form.querySelector(".lastName");
    var email = form.querySelector(".email");
    var birthday = form.querySelector(".birthday");
    var address1 = form.querySelector(".address1");
    var address2 = form.querySelector(".address2");
    var mobileNumber = form.querySelector(".mobileNumber");
    var confirmPassword = form.querySelector(".confirmPassword");
    var password = form.querySelector(".password");
    var validationArr = [firstName,lastName,birthday,email,address1,address2,mobileNumber,password,confirmPassword];
    var shouldReturn = false;
    for (let index = 0; index < validationArr.length; index++) {
        const element = validationArr[index];
        if(element.value.trim().length == 0){
            element.parentElement.classList.add("aleart");
            shouldReturn = true;
        }else{
            element.parentElement.classList.remove("aleart");
        }
    }
    /*if(password.value.trim().length < 8){
        password.parentElement.querySelector("small").innerText = "Password is short. Minimum 8 characters are required"
        password.parentElement.classList.add("aleart");
        return false;
    }
    if(password.value.trim() != confirmPassword.value.trim()){
        confirmPassword.parentElement.classList.add("aleart");
        return false;
    } */
    if(shouldReturn){
        return false;
    };
    // AJAX Request using the fetch
     const payload = new FormData(form);
     fetch("http://localhost:8080/savePatient", {
         method: 'POST',
         body: "tEST",
         })
         .then(async (response) => {
			 console.log(response);
			 //const jsonResult = await response.json();
			 //console.log(jsonResult);
		})
        .catch((error) => {
         console.error("Error:", error);
         });
         
  //  form.submit();
}); 
// doctor form submit event
doctorRegisterForm.addEventListener('click', function(event){
    event.preventDefault();
    var form = this.parentElement;
    var firstName = form.querySelector(".firstName");
    var lastName = form.querySelector(".lastName");
    var email = form.querySelector(".email");
    var birthday = form.querySelector(".birthday");
    var address1 = form.querySelector(".address1");
    var address2 = form.querySelector(".address2");
    var mobileNumber = form.querySelector(".mobileNumber");
    var registrationNumber = form.querySelector(".registrationNumber");
    var password = form.querySelector(".password");
    var confirmPassword = form.querySelector(".confirmPassword");
    var validationArr = [firstName,lastName,birthday,email,address1,address2,mobileNumber,registrationNumber,password,confirmPassword];
    var shouldReturn = false;
    for (let index = 0; index < validationArr.length; index++) {
        const element = validationArr[index];
        if(element.value.trim().length == 0){
            element.parentElement.classList.add("aleart");
            shouldReturn = true;
        }else{
            element.parentElement.classList.remove("aleart");
        }
    }
    if(password.value.trim().length < 8){
        password.parentElement.querySelector("small").innerText = "Password is short. Minimum 8 characters are required"
        password.parentElement.classList.add("aleart");
        return false;
    }
    if(password.value.trim() != confirmPassword.value.trim()){
        confirmPassword.parentElement.classList.add("aleart");
        return false;
    }
    if(shouldReturn){
        return false;
    };
    
    // AJAX Request using the fetch
    // const payload = new FormData(form);
    // fetch(registerFetchUrl, {
    //     method: 'POST',
    //     body: payload,
    //     })
    //     .then((response) => response.json())
    //     .then((data) => {
    //     console.log("Success:", data);
    //     })
    //     .catch((error) => {
    //     console.error("Error:", error);
    //     });
    form.submit();
});
// counsellor form submit event
counsellorRegisterForm.addEventListener('click', function(event){
    event.preventDefault();
    
    var form = this.parentElement;
    var firstName = form.querySelector(".firstName");
    var lastName = form.querySelector(".lastName");
    var email = form.querySelector(".email");
    var birthday = form.querySelector(".birthday");
    var address1 = form.querySelector(".address1");
    var address2 = form.querySelector(".address2");
    var mobileNumber = form.querySelector(".mobileNumber");
    var registrationNumber = form.querySelector(".registrationNumber");
    var password = form.querySelector(".password");
    var confirmPassword = form.querySelector(".confirmPassword");
    var validationArr = [firstName,lastName,birthday,email,address1,address2,mobileNumber,registrationNumber,password,confirmPassword];
    var shouldReturn = false;
    for (let index = 0; index < validationArr.length; index++) {
        const element = validationArr[index];
        if(element.value.trim().length == 0){
            element.parentElement.classList.add("aleart");
            shouldReturn = true;
        }else{
            element.parentElement.classList.remove("aleart");
        }
    }
     if(password.value.trim().length < 8){
        password.parentElement.querySelector("small").innerText = "Password is short. Minimum 8 characters are required"
        password.parentElement.classList.add("aleart");
        return false;
    }
    if(password.value.trim() != confirmPassword.value.trim()){
        confirmPassword.parentElement.classList.add("aleart");
        return false;
    }
    if(shouldReturn){
        return false;
    };
    // AJAX Request using the fetch
    // const payload = new FormData(form);
    // fetch(registerFetchUrl, {
    //     method: 'POST',
    //     body: payload,
    //     })
    //     .then((response) => response.json())
    //     .then((data) => {
    //     console.log("Success:", data);
    //     })
    //     .catch((error) => {
    //     console.error("Error:", error);
    //     });
    
    form.submit();
});

function throwConfetti() {
  var confettiContainer = document.getElementById("confetti");
  for (var i = 0; i < 100; i++) {
    var confetti = document.createElement("div");
    confetti.className = "confetti";
    confetti.style.left = Math.random() * 100 + "%";
    confetti.style.animationDelay = Math.random() * 5 + "s";
    confettiContainer.appendChild(confetti);
  }
}

// var isNumber = function(event) {
//     event = (event) ? event : window.event;
//     var charCode = (event.which) ? event.which : event.keyCode;
//     if (charCode > 31 && (charCode < 48 || charCode > 57)) {
//         return false;
//     }
//     return true;
// };
// mobileNum.addEventListener('keypress', function(event) {
//     return isNumber(event);
//     console.log("123")
// })
});

