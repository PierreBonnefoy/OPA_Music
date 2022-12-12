<script setup>
</script>

<template>
    <!DOCTYPE html>
    <html>
        <head>
            <title>Login</title>
            <link rel="stylesheet" href="/css/general.css">
            <link rel="stylesheet" href="/css/userForm.css">
        </head>
        <body>
            <div class="form">
                <h3 id="log">Login</h3>
                <div class="errorMessage" th:if="param.error">
                    [[${error}]]
                </div>
                <form v-on:submit.prevent="login">
                    <div><label> User Name : </label><input class="textField" type="text" name="username" maxlength="25" minlength="2" required="required" v-model="name"/> </div>
                    <div><label> Password : </label><input class="textField" type="password" name="password" minlength="2" required="required" v-model="password"/> </div>
                    <div><input class="button" id="login" type="submit" value="Sign In"></div>
                </form>
                <hr>
                    <a id="registerLink" th:href="@{/register}">
                        <input class="button" id="register" type="button" value="Register">
                    </a>
                    <router-link to="/" class="returnButton">Return to home page</router-link>
            </div>

        </body>
    </html>
</template>

<script>

export default {
  data() {
    return {
      posts: [],
    };
  },

  methods: {
    async login(e) {
    const req = await fetch('http://localhost:8080/api/loginVue', {
    method: 'POST',
    headers: {
        "Content-Type": 'application/json',
    },
    body: JSON.stringify({
        name: this.name,
        password: this.password,
    })
    })
    .then(async response => {
        const data = await response.json();
        if(!response.ok){
            const error = (data && data.message) || response.status;
            return Promise.reject(error);
        }
    
        this.postId = data.id;
    })
    .catch(error => {
        this.errorMessage = error;
        console.error("ERROR Bad Credentials :", error);
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