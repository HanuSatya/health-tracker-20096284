<!-- the "home-page" element is passed as a parameter to VueComponent in the JavalinConfig file -->
<template id="home-page">
  <app-layout>
    <div class="content">
      <div class="card">
        <h5 class="header">Registered Users</h5>
        <div class="body">
          <h5 class="title">{{users.length}} users</h5>
          <a href="/users" class="button">More Details...</a>
        </div>
      </div>
      <div class="card">
        <h5 class="header">Total Activities</h5>
        <div class="body">
          <h5 class="title">{{activities.length}} activities</h5>
          <a href="/activities" class="button">More Details...</a>
        </div>
      </div>
    </div>
  </app-layout>
</template>

<script>
Vue.component('home-page',
    {
      template: "#home-page",
      data: () => ({
        users: [],
        activities: []
      }),
      created() {
        axios.get("/api/users")
            .then(res => {
              console.log(res)
              this.users = res.data
            })
            .catch(() => alert("Error while fetching users"));
        axios.get("/api/activities")
            .then(res => this.activities = res.data)
            .catch(() => alert("Error while fetching activities"));
      }
    });
</script>

<style>
  .content{
    width: 100%;
    margin-top: 70px;
    display: flex;
    justify-content: space-around;
  }
  .card {
    width: 400px;
    height: fit-content;
    padding: 10px 15px;
    background: rgba(255, 255, 255, 0.625);
    border-radius: 12px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    backdrop-filter: blur(5px);
    -webkit-backdrop-filter: blur(5px);
    border: 1px solid rgba(255, 255, 255, 0.3);
    transition: all 0.3s cubic-bezier(0.23, 1, 0.320, 1);
  }
  .header{
    width: 100%;
    padding: 15px 15px;
    border-radius: 8px;
    background: rgba(0,0,0,.06);
    color: #000;
    font-size: 20px;
    font-weight: 700;
    transition: all 0.3s ease;
  }
  .body{
    margin: 20px 0px;
    padding: 10px;
    align-items: center;
    justify-content: space-between;
    display: flex;
  }
  .card:hover{
    box-shadow: 0 4px 30px rgba(0, 0, 0, 0.2);
    transform: translateY(-3px);
  }
  .button{
    /* material button sytles */
    display: inline-block;
    padding: 10px 20px;
    border-radius: 6px;
    background: rgb(36, 36, 36);
    color: #fff;
    font-size: 16px;
    font-weight: 600;
  }
  .title{
    font-size: 20px;
    font-weight: 600;
    margin: 0;
  }
  .button:hover{
    color: white;
  }
</style>