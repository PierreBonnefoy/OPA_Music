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
            <title>Login</title>
        </head>
        
        <body>
            <div class="form">
                <h3 id="logTitle">Login</h3>
                <form v-on:submit.prevent="login">
                    <div><label> User Name : </label><input class="textField" type="text" name="username" maxlength="25" minlength="2" required="required" v-model="username"/> </div>
                    <div><label> Password : </label><input class="textField" type="password" name="password" minlength="2" required="required" v-model="password"/> </div>
                    <div><input class="button" id="loginForm" type="submit" value="Sign In"></div>
                </form>
                <hr>
                    <div id="registerLink"><router-link to="/register" class="button" id="registerForm">Register</router-link></div>
                    <router-link to="/" class="returnButton">&lt  return to home page</router-link>
            </div>

        </body>
    </html>
</template>

<script>
export default{
    data: () => {
        return {
            username: "",
            password: "",
        };
    },
    methods:{
        
        async login(e){
            e.preventDefault();
            const response = await fetch("http://localhost:8080/api/auth/signin", {
                method : "POST",
                headers : {
                    "Content-Type" : "application/json",
                },
                body: JSON.stringify({
                    username: this.username,
                    password: this.password,
                }),
            })
            .then(blob => blob.json())
            .then(data => {
                if(data.token){
                    console.log(data)
                    localStorage.setItem('token', data.token)
                    localStorage.setItem('username', data.username)
                    localStorage.setItem('logged', true)
                    this.$router.push('/')
                }
                else{
                    alert("Bad Credentials")
                }
            })
            .catch(err => console.log(err))
           
        },
    },
};
</script>