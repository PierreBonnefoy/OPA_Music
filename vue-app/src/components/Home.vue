<script setup>
import '../assets/css/general.css'
import '../assets/css/home.css'
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
        <RouterLink v-if="!logged" id=reg class=button to="/register">Register</RouterLink>
        <RouterLink v-if="!logged" id=log class=button to="/logIn">Log In</RouterLink>
        <RouterLink v-if="logged" id=fav class=button to="/favorites">Favorites</RouterLink>
      </nav>
    </div>
    </header>
    <body>
        <div id="mainPage">
            <span>{{username}}</span>
            <a class="return">
                <img id="logo" src="../assets/images/logo.svg" >
            </a>

            <input v-if="logged"  @click="logout" id="logout" class="button" type="submit" value="Sign Out" />

             <!-- Playlist menu 
            <div id="playlistChoice" v-show="displayMenu">
                <input type="button" value="X" @click="displayMenu = !displayMenu" /> 
                
                <h3>Choose a playlist</h3>
                
                <form>
                    <div v-for="playlist in playlists" :content="playlist">
                        <input type="checkbox" name={{playlist}}/>
                        <label for={{playlist}}>{{playlist}}</label>
                    </div>
                    <p>{{selectedPlaylists}}</p>

                    <a>
                    <input type="button" value="add" @click="displayMenu = !displayMenu" /> 
                    </a>
                </form>

                <hr>
                <div>
                    <h3>Create a new playlist : {{newPlaylist}}</h3>
                    <input v-bind:value="newPlaylist" v-model="newPlaylist" type="text" v-on:keyup.enter="createPlaylist">
                    <input type="button" value="Create" @click="createPlaylist" v-if="newPlaylist != ''"/>
                </div>
            </div>-->

            <form v-on:submit.prevent="research">
                <input class="SearchSpace" type="text" id="search" placeholder="Search" v-model="search" v-value ="search"/>
                <input id="sea" class="button" type="submit" value="Search"/>
            </form>

            <br>
            <!-- Load all videos -->
            <span id="#musicList" v-for="vi in videos">
                <iframe id="music" :src = "vi" width="420" height="315" frameborder="0" allowfullscreen><br></iframe>
                
                <a id="addFav">
                    <input id="addFavButton" class="button" type="button" value="⭐️" @click="displayMenu = !displayMenu">
                </a>
            </span>
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
            let key = "AIzaSyDbX-pXMguUhzBsu4a71kIIBFGvyKcjhuY"
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
    },
}

</script>