let app = Vue.createApp({
    data: () =>({
        template: ['<search/>','<videos/>','<head_main>'],
    })
})

app.component('videos', {
    template: `
    <div id="musicList">
        <span data-th-each="v : {{ videos }}">
            <iframe id="music" width="420" height="315" data-th-src="'http://www.youtube.com/embed/' + {{ v.url }} + '?showinfo=0&modestbranding=1'" frameborder="0" allowfullscreen><br></iframe>
            <a id="addFav" data-th-href="@{/addFav/{link}&{mail} (link={{ v.url }},mail={{ user }})}">
                <input id="addFavButton" class="button" type="button" value="⭐️" sec:authorize="isAuthenticated()">
            </a>
        </span>
    </div>  `,
    props : ["videos","user"],
})

app.component('search', {
    template:`
    <form data-th-action="@{/search}" data-th-object="{{ search }}" method="POST">
        <input class="SearchSpace" type="text" id="search" data-th-field="*{search}" placeholder="Search" data-th-value = "{{ search.search }}">
        <input id="sea" class="button" type="submit" value="Search">
    </form>
    `,
    props : ["search"],
})

app.component('head_main',{
    template: `
    <span id="userName" sec:authorize="isAuthenticated()" th:text="{{ name }}"></span>
            
        <a th:href="@{/register}" id="reg" sec:authorize="isAnonymous()">
            <input id="register" class="button" type="button" value="REGISTER"> 
        </a>
        <a th:href="@{/loginRedirect}" id="log" sec:authorize="isAnonymous()">
            <input id="login" class="button" type="button" value="LOG IN"> 
        </a>
        <form id="out" th:action="@{/logout}" method="post" sec:authorize="isAuthenticated()">
                <input id="logout" class="button" type="submit" value="Sign Out" />
        </form>
            
        <a id="fav" data-th-href="@{/fav/{mail} (mail={{ mail }}">
            <input id="favorite" class="button" type="button" value="⭐️ Favorites" sec:authorize="isAuthenticated()">
        </a>

        <a class="return" data-th-href="@{/clear}">
            <img id="logo" src="/images/logo.svg" >
        </a>`,
        props : ['name','mail'],
})


let vm = app.mount('#container')