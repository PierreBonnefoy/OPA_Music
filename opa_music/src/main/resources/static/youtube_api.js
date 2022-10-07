
$(document).ready(function(){

    var url = "https://www.googleapis.com/youtube/v3/search"
    var type = "video"
    var part = "snippet"
    var maxResults = 3
    var key = "AIzaSyD5M5IzAQRkvydUZ12viKfkUzTwSa-BPAY"
    
    var video = ''
    
    $("#reseach").submit(function(event){
        event.preventDefault()

        var search = $("#search").val()

        $("#videoSpace").empty()

        var query = url+"?key="+key+"&type="+type+"&part="+part+"&maxResults="+maxResults +"&q="+search

        $.get(query,function(data){
            data.items.forEach(item => {

                video = `
                <iframe width="420" height="315" src="http://www.youtube.com/embed/${item.id.videoId}" frameborder="0" allowfullscreen></iframe>
                `

                $("#videoSpace").append(video)
            });
        })
    })
})