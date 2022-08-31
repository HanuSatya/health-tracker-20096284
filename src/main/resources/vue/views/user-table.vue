<template id="user-table">
  <app-layout>
    <table class="table">
      <thead>
      <tr>
        <th scope="col">#</th>
        <th scope="col">Healthy</th>
        <th scope="col">UnHealthy</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(user, index) in healthy">
        <th scope="row">{{ index+1 }}</th>
        <td>{{ user.full_name }}</td>
        <td>{{ unhealthy[index]?unhealthy[index].full_name:"----" }}</td>
      </tr>
      </tbody>
    </table>
  </app-layout>
</template>

<script>
Vue.component("user-table", {
  template: "#user-table",
  data: () => ({
    users: [],
    activities: [],
    analysis: [],
    healthy: [],
    unhealthy: [],
    formData: [],
    hideForm :true,
  }),
  created() {
    this.fetchUsers();
    this.fetchActivities()
  },
  methods: {
    fetchUsers: function () {
      axios.get("/api/users")
          .then(res => {
            this.users = res.data
            this.analyze()
          })
          .catch(() => alert("Error while fetching users"));
    },
    fetchActivities: function () {
      axios.get("/api/activities")
          .then(res => {
            this.activities = res.data
            this.analyze()
          })
          .catch(() => alert("Error while fetching activities"));
    },
    analyze: function () {
      this.analysis = [];
      if (this.activities.length > 0 && this.users.length > 0) {
        this.users.forEach(user => {
          let activity;
          this.activities.forEach(ac => {
            if (ac.userId === user.id) activity = ac;
          })
          if (activity) {
            const cal = this.getCalories(user.age)
            if (cal - activity.calories <= 1500) this.healthy.push(user);
            else this.unhealthy.push(user)
          }
        })
      }
    },
    getCalories: function (age){
      if(age>15 && age<20) return 2000;
      else if(age>20 && age<30) return 2500;
      else if(age>30 && age<40) return 3000;
      else if(age>40 && age<50) return 2500;
      else if(age>50 && age<70) return 2000;
      else return 1500;
    }
  }
});
</script>

<style scoped>
.grid{
  display: grid;
  gap: 10px;
  grid-template-columns: auto auto;
}
</style>