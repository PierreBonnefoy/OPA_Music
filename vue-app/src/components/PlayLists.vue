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

            <a class="return" data-th-href="@{/clear}">
                <img id="logo" src="../assets/images/logo.svg" >
            </a>
            
            <h2 id="playlistTitle">Playlists</h2>
            <!-- Load all videos -->

            <div id="playlistTabs">
                <span v-for="n in names">
                    <input id="playlistTab" class="button" type="submit" :value="n.name" @click="showPlayList(n.id)"/>
                </span>
            </div>

            <a id="delPlaylist">
                <input id="delPlaylistButton" class="button" type="button" value="🗑️" @click="supprPlay">
            </a>
            <h3 id="playlistTitle" v-bind:value="name"><i>Le nom de la playlist</i></h3>

            <span id="#musicList" v-for="vi in videos">
                <iframe id="music" :src = "vi" width="420" height="315" frameborder="0" allowfullscreen><br></iframe>
                
                <a id="addfavvue">
                    <input id="delFavButton" class="button" type="button" value="🗑️" @click="supprOfPlay(vi)">
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
            name: "",
            names: [],
            playlist: "",
            username: localStorage.getItem("username"),
            logged: localStorage.getItem("logged"),
        };
    },
    created() {
        this.loadAll()
    },

    
  
    methods: {
        logout(){
            localStorage.removeItem('token')
            localStorage.removeItem('username')
            localStorage.removeItem('logged')
            this.$router.push("/login")
        },

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
        async showPlayList(p){
            this.videos = []
            this.playlist = p
            const req = await fetch('http://localhost:8080/api/showPlayList', {
                method: 'POST',
                headers: {
                    "Content-Type":'application/json',
                },
                body: JSON.stringify({
                    username : this.username,
                    pid : p,
                })
            }).then((response) => response.json())
            .then((json)=>{
                console.log(json)
                for(let i of json){
                    this.videos.push(i.url)
                }
            })
        },
        async supprOfPlay(v){
            const req = await fetch('http://localhost:8080/api/supprOfPlay', {
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
            this.showPlayList(this.playlist)
        },
        async supprPlay(){
            const req = await fetch('http://localhost:8080/api/supprPlay', {
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
            this.loadAll()
        }
    },
}
</script>