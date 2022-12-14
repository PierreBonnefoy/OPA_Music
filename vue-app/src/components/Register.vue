<script setup>
import "../assets/css/userForm.css"
</script>

<template>
    <!DOCTYPE html>
<html>

<head>
    <meta charset="ISO-8859-1">
    <title>Register</title>
</head>

<body>
    <div class="form">
        <h3 id="reg">Register</h3>
        <div class="errorMessage" th:text="${msg}"></div>

        <form v-on:submit.prevent="register">
            <div><label>Name : </label><input class="textField" type="text" name="name" maxlength="25" minlength="2" required="required" v-model="name"/></div>
            <div><label>Email : </label><input class="textField" type="text" name="email" minlength="2" required="required" v-model="email"/></div>
            <div><label>Password : </label><input class="textField" type="password" name="password" minlength="2" required="required" v-model="password"/></div>
            <input class="button" id="login" type="submit" value="Register"/>
        </form>
        
        <input class="returnButton" type="button" onclick="window.location='/'" value="<  return to home page"/>
    </div>
</body>

</html>
</template>

<script>

export default {
  data() {
    return {
      name : "",
      password : "",
      email: "",
    };
  },

  methods: {
    async register(e) {
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
        const data = await response.json();
        if(data.status == 0){
            const error = (data && data.message) || response.status;
            alert("Username already taken")
            return Promise.reject(error);
        }

        this.postId = data.id;
        this.$router.push('/login')
    })
    .catch(error => {
        this.errorMessage = error;
        console.error("ERROR Username already taken :", error);
    })

    },

    async getData() {
      try {
        let response = await fetch("http://localhost:8080/api/users");
        this.posts = await response.json();
      } catch (error) {
        console.log(error);
      }
    },
  },

  created() {
  },
};
</script>