<template id="user-bmi">
  <app-layout>
    <div class="grid2">
      <div class="list-group-item d-flex align-items-start"
           v-for="(user,index) in users" v-bind:key="index">
        <div class="mr-auto p-2 row">
          <div class="card col" style="width: 18rem;">
            <div class="card-body">
              <h5 class="card-title">{{ user.full_name }}</h5>
              <h6 class="card-subtitle mb-2 text-muted">{{ user.phone_number }}</h6>
              <p class="card-subtitle mb-2 text-muted">{{ user.email_id }}</p>
              <p class="card-text">Address: {{ user.address }}</p>
            </div>
          </div>
          <div class="col">
            <h6 class="bg-light p-2">Health Analysis</h6>
            <div v-if="analysis[index]" v-for="(val, index) in analysis[index]" >
              <p style="margin-bottom: 0">{{ val }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </app-layout>
</template>

<script>
Vue.component("user-bmi", {
  template: "#user-bmi",
  data: () => ({
    users: [],
    activities: [],
    analysis: [],
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
            let ishealthy = "unhealthy";
            if(cal-activity.calories<=1500) ishealthy = "healthy"
            this.analysis.push(["Activity Found " + activity.description,
              "Calories Burned " + activity.calories,
              "Activity Duration " + activity.duration,
              "Based on Analysis ",
              "You are currently "+ishealthy]);
          } else this.analysis.push(["No activities Found"])
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
.grid2{
  display: grid;
  gap: 10px;
  grid-template-columns: auto auto;
}
</style>