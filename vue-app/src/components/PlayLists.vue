<script setup>
</script>

<template>
    <!DOCTYPE html>
    <html>
        <head>
            <meta charset="utf-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <title>PlayLists | OPAMusic</title>
            <meta name="description" content="">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <link rel="stylesheet" href="/css/general.css">
            <link rel="stylesheet" href="/css/home.css">
            <link rel="shortcut icon" type="/image/png" href="/images/icon.png">
        </head>
        <body>
            <span id="userName">{{username}}</span>
            
            <div id="out"><input v-if="logged" @click="logout" id="logout" class="button" type="submit" value="Sign Out" /></div>

            <a class="return" href="/">
                <img id="logo" src="../assets/images/logo.svg" >
            </a>
            
            <h2 id="playlistTitle">Playlists</h2>
            <!-- Load all videos -->

            <div id="playlistTabs">
                <span v-for="n in names">
                    <input id="playlistTab" class="button" type="submit" :value="n.name" @click="showPlayList(n)"/>
                </span>
            </div>

            <a id="delPlaylist">
                <input id="delPlaylistButton" class="button" type="button" v-if="show" value="🗑️" @click="delPlay">
            </a>
            <h3 id="playlistTitle">{{ name }}</h3>

            <span id="#musicList" v-for="vi in videos">
                <iframe id="music" :src = "vi" width="420" height="315" frameborder="0" allowfullscreen><br></iframe>
                
                <a id="addfavvue">
                    <input id="delFavButton" class="button" type="button" value="🗑️" @click="delOfPlay(vi)">
                </a>
            </span>      
        </body>
    </html>
</template>

<script>
    
export default {
    data() {
        return {
            show : false,
            videos: [],
            names: [],
            name : "",
            playlist: "",
            username: localStorage.getItem("username"),
            logged: localStorage.getItem("logged"),
        };
    },
    created() {
        this.loadAll()
    },

    
  
    methods: {
        /* Logout method */
        logout(){
            localStorage.removeItem('token')
            localStorage.removeItem('username')
            localStorage.removeItem('logged')
            this.$router.push("/login")
        },
        /* Load all PlayLists in buttons and show only their names */
        async loadAll(){
            this.names = []
            const req = await fetch('http://localhost:8080/api/loadAll', {
                method: 'POST',
                headers: {
                    "Content-Type":'application/json',
                },
                body: JSON.stringify({
                    username : this.username,
                })
            }).then((response) => response.json())
            .then((json)=>{this.names = json})
        },
        /* Show all music in selected PlayList p */
        async showPlayList(p){
            this.videos = []
            this.playlist = p.id
            this.name = p.name
            this.show = true
            const req = await fetch('http://localhost:8080/api/showPlayList', {
                method: 'POST',
                headers: {
                    "Content-Type":'application/json',
                },
                body: JSON.stringify({
                    username : this.username,
                    pid : p.id,
                })
            }).then((response) => response.json())
            .then((json)=>{
                for(let i of json){
                    this.videos.push(i.url)
                }
            })
        },
        /* Remove selected music v of the current PlayList */
        async delOfPlay(v){
            const req = await fetch('http://localhost:8080/api/delOfPlay', {
                method: 'POST',
                headers: {
                    "Content-Type":'application/json',
                },
                body: JSON.stringify({
                    user : this.username,
                    pid : this.playlist,
                    url : v,
                })
            })
            this.showPlayList({id:this.playlist,name:this.name})
        },
        /* Remove entirely PlayList of database */
        async delPlay(){
            this.show = false
            const req = await fetch('http://localhost:8080/api/delPlay', {
                method: 'POST',
                headers: {
                    "Content-Type":'application/json',
                },
                body: JSON.stringify({
                    user : this.username,
                    pid : this.playlist,
                })
            })
            this.videos = []
            this.playlist = ""
            this.name = ""
            this.loadAll()
        }
    },
}
</script>