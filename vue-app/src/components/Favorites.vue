<script setup>
</script>

<template>
    <!DOCTYPE html>
    <html>
        <head>
            <meta charset="utf-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <title>Favorites | OPAMusic</title>
            <meta name="description" content="">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <link rel="stylesheet" href="/css/general.css">
            <link rel="stylesheet" href="/css/home.css">
            <link rel="shortcut icon" type="/image/png" href="/images/icon.png">
        </head>
        <body>
            <span id="userName">{{username}}</span>
            
            <div id="out"><input v-if="logged" @click="logout" id="logout" class="button" type="submit" value="Sign Out" /></div>

            <a class="return" data-th-href="@{/clear}">
                <img id="logo" src="../assets/images/logo.svg" >
            </a>
            
            <h2 id="favoriteTitle">Favorites</h2>
            <!-- Load all videos -->
            <span id="#musicList" v-for="vi in videos">
                <iframe id="music" :src = "vi" width="420" height="315" frameborder="0" allowfullscreen><br></iframe>
                
                <a id="addfavvue">
                    <input id="delFavButton" class="button" type="button" value="🗑️" @click="delfavvue(vi)">
                </a>
            </span>      
        </body>
    </html>
</template>

<script>
    
export default {
    data() {
        return {
            videos: [],
            username: localStorage.getItem("username"),
            logged: localStorage.getItem("logged"),
        };
    },
    created() {
        this.favvue()
    },

    
  
    methods: {
        /* Logout method */
        logout(){
            localStorage.removeItem('token')
            localStorage.removeItem('username')
            localStorage.removeItem('logged')
            this.$router.push("/login")
        },
        /* Remove music v from Favorites for current user */
        async delfavvue(v){
            const req = await fetch('http://localhost:8080/api/delfavvue', {
                method: 'POST',
                headers: {
                    "Content-Type":'application/json',
                },
                body: JSON.stringify({
                    user : this.username,
                    url : v,
                })
            })
            this.favvue()
        },
        /* Load all current user Favorites' */
        async favvue(){
            this.videos = []
            const req = await fetch('http://localhost:8080/api/favvue', {
                method: 'POST',
                headers: {
                    "Content-Type":'application/json',
                },
                body: JSON.stringify({
                    username : this.username,
                })
            }).then((response) => response.json())
            .then((json)=>{
                for(let i of json){
                    this.videos.push(i.url)
                }
            })
        },
    },
}
</script>