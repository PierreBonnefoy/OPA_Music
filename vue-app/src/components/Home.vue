<script setup>
import { resolveTransitionHooks } from 'vue';
import '../assets/css/general.css';
import '../assets/css/home.css';
</script>

<template>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Home | OPAMusic</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="shortcut icon" type="/image/png" href="/images/icon.png">
        
    </head>
    <header>
    <div class="wrapper">

      <nav>
        <div id="reg">
            <RouterLink v-if="!logged" id=register class=button to="/register">REGISTER</RouterLink>
        </div>
        <div id="log">
            <RouterLink v-if="!logged" id=login class=button to="/logIn">LOG_IN</RouterLink>
        </div>
        <div id="favButton">
            <RouterLink v-if="logged" id=favorite class=button to="/favorites">⭐️ Favorites</RouterLink>
        </div>
        <div id="playlistButton">
            <RouterLink v-if="logged" id=playlist class=button to="/playlists"><span class="blue">▶</span> Playlists</RouterLink>
        </div>
      </nav>
    </div>
    </header>
    <body>
        <div id="mainPage">
            <span id="userName">{{username}}</span>
            <a class="return" href="/">
                <img id="logo" src="../assets/images/logo.svg" >
            </a>

            <div id="out"><input v-if="logged"  @click="logout" id="logout" class="button" type="submit" value="Sign Out" /></div>



            <!-- playlist menu -->
            <div id="playlistChoice" v-show="displayMenu">
                <input id="crossClose" class="button" type="button" value="X" @click="ChangeDisplayMenu" />
            
                <h3>Choose a playlist</h3>
            
                <form>
                    <div id="checkboxPlaylist" v-for="playlist in playlists">
                        <input class="checkButton" type="checkbox" v-bind:id="playlist.id" @click="()=>{selectPlaylist(playlist.id)}"
                            v-model="playlist.selected" />
                        <label id="nomPlaylist" v-bind:for="playlist.id">{{playlist.name}}</label>
                    </div>
            
                    <a>
                        <input id="playlistMenuButton" class="button" type="button" value="Add" @click="addInPlaylist" />
                    </a>
                </form>
            
                <hr>
                <div>
                    <h3>Create a new playlist : {{newPlaylist}}</h3>
                    <input id="textFieldPlaylist" v-model="newPlaylist" type="text" v-on:keyup.enter="createPlaylist" />
                    <input id="playlistMenuButton" class="button" type="button" value="Create" @click="createPlaylist"
                        v-if="newPlaylist != ''" />
                </div>
            </div>



            <form v-on:submit.prevent="research">
                <input class="SearchSpace" type="text" id="search" placeholder="Search" v-model="search" v-value ="search"/>
                <input id="sea" class="button" type="submit" value="Search"/>
            </form>

            <br>
            <!-- Load all videos -->
            <div id="musicList">
                <span v-for="vi in videos">
                    <iframe id="music" :src = "vi" width="420" height="315" frameborder="0" allowfullscreen><br></iframe>
                    
                    <a id="addfavvue">
                        <input id="addFavButton" class="button" type="button" value="⭐️" @click="addfavvue(vi)">
                    </a>
                    <a>
                        <input id="addPlaylistButton" class="button blue" type="button" value="▶"  @click="ChangeDisplayMenu(vi)">
                    </a>
                </span>
            </div>
        </div>
    </body>

</template>
<script>

export default {
    data() {
        return {
            posts: [],
            videos: [],
            username: localStorage.getItem("username"),
            logged: localStorage.getItem("logged"),

            // playlist menu
            displayMenu: false,
            newPlaylist: "New Playlist",
            playlists: [],
            selectedPlaylists: [],
            selectedVideo: "",
        };
    },

    
  
    methods: {
        //Proceed the logout by clearing the local storage.
        logout(){
            localStorage.removeItem('token')
            localStorage.removeItem('username')
            localStorage.removeItem('logged')
            this.$router.push("/login")
        },
        /* Method of the search bar (Youtube's API) */
        async research(){
            this.videos = []
            this.search = this.search.replace("\\s","+")
            let max = 12

            // Make the search 
            let url = "https://www.googleapis.com/youtube/v3/search"
            let key = "AIzaSyD5M5IzAQRkvydUZ12viKfkUzTwSa-BPAY"
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
        /* Method add music v in current's user Favorites */
        async addfavvue(v){
            const req = await fetch('http://localhost:8080/api/addfavvue', {
                method: 'POST',
                headers: {
                    "Content-Type":'application/json',
                },
                body: JSON.stringify({
                    user : this.username,
                    url : v,
                })
            })
        },


        // playlist menu

        /* Method to open or close the playlist menu (and reinitialize it) */
        async ChangeDisplayMenu(v) {

            this.playlists = []
            const req = await fetch('http://localhost:8080/api/loadAll', {
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
                    this.playlists.push({id : i.id,name : i.name, selected:false})
                }
            })

            // reset form
            this.selectedPlaylists = [];
            this.newPlaylist = "New Playlist";
            this.selectedVideo = v
            // show or hide menu
            this.displayMenu = !this.displayMenu;
        },

        /* Method to create a playlist */
        async createPlaylist() {
            const req = await fetch('http://localhost:8080/api/addplaylist', {
                    method: 'POST',
                    headers: {
                        "Content-Type":'application/json',
                    },
                    body: JSON.stringify({
                        user : this.username,
                        name : this.newPlaylist,
                    })
                }).then((response) => response.json())
                .then((json) =>{
                    this.playlists.push({ id: json.id, name: json.name, selected: true })
                    this.selectPlaylist((json.id).toString());
                })
            
            this.newPlaylist = "New Playlist";
        },

        /* Method to select a playlist in the checkbox */
        selectPlaylist(playlist) {
            let selectedPlaylist = playlist.toString();
            // if already selected
            if (this.selectedPlaylists.includes(selectedPlaylist)) {
                this.selectedPlaylists = this.selectedPlaylists.filter(item => !selectedPlaylist.includes(item));
            }
            else { // if not
                this.selectedPlaylists.push(selectedPlaylist);
            }
        },

        /* Mathod add current music to the selected playlists */
        async addInPlaylist() {
            
            for (let p2 of this.selectedPlaylists) {
                let p = parseInt(p2);
                const req = await fetch('http://localhost:8080/api/addtoplaylist', {
                    method: 'POST',
                    headers: {
                        "Content-Type":'application/json',
                    },
                    body: JSON.stringify({
                        user : this.username,
                        url : this.selectedVideo,
                        pid : p,
                    })
                })
            }

            this.ChangeDisplayMenu();
        },
    },
}





</script>