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
            
            
            <form v-on:submit.prevent="research">
                <input class="SearchSpace" type="text" id="search" placeholder="Search" v-model="search" v-value ="search"/>
                <input id="sea" class="button" type="submit" value="Search"/>
            </form>
            <br>
            <h2 id="favoriteTitle">Favorites</h2>
            <!-- Load all videos -->
            <div  id="loadFavorites"><input class="button" type="submit" value="Load" @click="favvue()"/></div>
            <span id="#musicList" v-for="vi in videos">
                <iframe id="music" :src = "vi" width="420" height="315" frameborder="0" allowfullscreen><br></iframe>
                
                <a id="addfavvue">
                    <input id="delFavButton" class="button" type="button" value="🗑️" @click="supprfavvue(vi)">
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

    
  
    methods: {
        logout(){
            localStorage.removeItem('token')
            localStorage.removeItem('username')
            localStorage.removeItem('logged')
            this.$router.push("/login")
        },
        async research(){
            this.videos = []
            this.search = this.search.replace("\\s","+")
            let max = 12
        
        // Make the search 
        let url = "https://www.googleapis.com/youtube/v3/search"
        let key = "AIzaSyCPciyCY789MtbHofF9M05AVx-p0DtXq_0"
        let type = "video"
        let part = "snippet"
        let maxResults = max
        let search = this.search
        
        await fetch(url+"?key="+key+"&type="+type+"&part="+part+"&maxResults="+maxResults +"&q="+search)
        .then((response) => response.json())
        .then((json)=>{
            for(let i=0; i<12 ;i++){
                this.videos.push("http://www.youtube.com/embed/" + json.items[i].id.videoId+ "?showinfo=0&modestbranding=1")
            }})
        },
        async supprfavvue(v){
            const req = await fetch('http://localhost:8080/api/supprfavvue', {
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