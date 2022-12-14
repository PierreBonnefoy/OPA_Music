//<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
function research(search){
    let videos = []
    search = search.replace("\\s","+")
    max = 12
    // Make the search 
    $.ajax({
        url : "https://www.googleapis.com/youtube/v3/search",
        type : "GET",
        data : {
            // Data of the search 
            part : "snippet",
            type : "video",
            maxResults : max,
            key : "AIzaSyD5M5IzAQRkvydUZ12viKfkUzTwSa-BPAY",
            q : search
        }
    }).done(function(found){
        // Get video's url 
        for(i=0;i<max;i++){
            videos.push(found.items[i].id.videoId)
        }
    })

    return videos
}