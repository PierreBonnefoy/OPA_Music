<script setup>
</script>

<style scoped>
@import "../assets/css/userForm.css";
</style>

<template>
    <!DOCTYPE html>
<html>

<head>
    <meta charset="ISO-8859-1">
    <title>Register</title>
</head>

<body>
    <div class="form">
        <h3 id="regTitle">Register</h3>
        <div class="errorMessage" th:text="${msg}"></div>

        <form v-on:submit.prevent="register">
            <div><label>Name : </label><input class="textField" type="text" name="name" maxlength="25" minlength="2" required="required" v-model="name"/></div>
            <div><label>Email : </label><input class="textField" type="text" name="email" minlength="2" required="required" v-model="email"/></div>
            <div><label>Password : </label><input class="textField" type="password" name="password" minlength="2" required="required" v-model="password"/></div>
            <input class="button" id="loginForm" type="submit" value="Register"/>
        </form>
        
        <input class="returnButton" type="button" onclick="window.location='/'" value="<  return to home page"/>
    </div>
</body>

</html>
</template>

<script>

export default {
  //Initialization of the data
  data() {
    return {
      name : "",
      password : "",
      email: "",
    };
  },

  methods: {
    //Method wich do the register action.
    async register(e) {
    //POST request on the REST API
    const req = await fetch('http://localhost:8080/api/register', {
      method: 'POST',
      headers: {
          "Content-Type": 'application/json',
      },
      body: JSON.stringify({
          name: this.name,
          password: this.password,
          email: this.email
      })
    })
    .then(async response => {
        //waiting for the response of the API
        const data = await response.json();
        //Verification if the user have been created.
        if(data.status == 0){
            const error = (data && data.message) || response.status;
            alert("Username already taken")
            return Promise.reject(error);
        }

        this.postId = data.id;
        //Redirection to the login page
        this.$router.push('/login')
    })
    .catch(error => {
        this.errorMessage = error;
        console.error("ERROR Username already taken :", error);
    })
    }
  },
};
</script>