console.log(localStorage.getItem('id'));
      let id = '0';
      let key = '0';
      $.ajax({
        url: 'api/get_tournaments',
        method: 'post',
        dataType: 'html',
        data: {user: id},
        success: function(data){
          console.log(data);
        },
        error: console.log('не отправился я')
      });

