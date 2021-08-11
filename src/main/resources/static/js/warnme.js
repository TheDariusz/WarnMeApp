document.addEventListener("DOMContentLoaded", function() {
  const registerForm = document.getElementById("registerForm");
  const password1 = document.getElementById("password");
  const password2 = document.getElementById("password2");
  const password_check_message = document.getElementById("message").firstElementChild;

  registerForm.addEventListener('submit', function (e) {
    if (password1.value !== password2.value) {
      e.preventDefault();
      password_check_message.innerText = "Passwords do not match!";
    } else {
      password_check_message.innerText = "";
    }
  })
});