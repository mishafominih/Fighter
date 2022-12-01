$.ajax({
  url: "/api/get_tournaments",
  method: "post",
  dataType: "json",
  data: {'user': localStorage.getItem("user_id")}, 
  success: function (data) {
    console.log(data);
   
  },
  error: function (xhr, status) {
    console.log(xhr.status);
  },
});

