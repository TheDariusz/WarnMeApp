document.addEventListener("DOMContentLoaded", function() {
  const registerForm = document.getElementById("registerForm");
  const password1 = document.getElementById("password");
  const password2 = document.getElementById("password2");
  const message = document.getElementById("message").firstElementChild;

  registerForm.addEventListener('submit', function (e) {
    if (password1.value !== password2.value) {
      e.preventDefault();
      message.innerText = "Passwords do not match!";
    } else {
      message.innerText = "";
    }
  })

  registerForm.addEventListener('change', function (e) {
    const emailReg = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/
    if (!emailReg.test(registerForm.username.value)) {
      console.log(registerForm.username.value);
      e.preventDefault();
      message.innerText="password has invalid format!"
    } else {
      message.innerText = "";
    }
  })
});