console.log(localStorage.getItem('tournament_id'))
$.ajax({
  url: "http://51.250.97.3/api/get_players",
  method: "post",
  dataType: "json",
  data: {'tournament_id': localStorage.getItem('tournament_id'), 'user': 16}, 
  success: function (data) {
    console.log(data);
    
    for(let i = 0; i < data.length; i++){
      let result = '';
      let  json = JSON.parse(data[i]['categories']);
      if(data[i]['categories']){
        for(let j = 0; j < json.length; j++){
        result += json[j]["value"] + ' ';
      }
      }
      
      $('table').append(`<tr><td>` + data[i]['name'] + `</td><td>`+ result+ `</td><td>` + data[i]['description']+ `</td></tr>`)
      let table = $(".bodyT");
            for (let i = 0; i < table.length; i++) {
              table[i].addEventListener("click", function () {
                localStorage.setItem("tournament_id", data[i]['key']);
                console.log(localStorage.getItem("tournament_id"));
              });
            }
    }
  },
  error: function (xhr, status) {
    console.log(xhr.status);
  },
});