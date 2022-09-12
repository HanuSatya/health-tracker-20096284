<template id="user-analytics">
  <app-layout>
    <div class="card bg-light" style="padding: 0px; margin: 20px 0 30px 40px; width: 70%;">
      <div class="card-header">
        <div class="row">
          <div class="col-6" style="font-weight: 700;">
            Add Analytics
          </div>
        </div>
      </div>
      <div class="card-body" style="display: flex; flex-direction: column; align-items: flex-end;">
        <form id="addUserAnalytics" style="width: 100%;">
          <div style="display: flex;">
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-user-name">User Name</span>
              </div>
              <select style="width: 70%;" class="form-select form-select-sm" v-model="formData.user_id">
                <option v-for="(user, index) in users"  v-bind:value="user.id">{{user.full_name}}</option>
              </select>
            </div>
            <div class="input-group mb-3 ml-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-user-phone">Calories Intake</span>
              </div>
              <input type="number" class="form-control" v-model="formData.calorie_intake" name="calorie_intake" placeholder="Ex: 2000"/>
            </div>
          </div>
          <div style="display: flex;">
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-user-name">Height</span>
              </div>
              <input type="number" class="form-control" v-model="formData.height" name="height" placeholder="Ex: 170 (in CM)"/>
            </div>
            <div class="input-group mb-3 ml-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-user-phone">Weight</span>
              </div>
              <input type="number" class="form-control" v-model="formData.weight" name="weight" placeholder="Ex: 80 (in KG's) "/>
            </div>
          </div>
          <div style="display: flex;">
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-user-name">Average Sleep</span>
              </div>
              <input type="number" class="form-control" v-model="formData.avg_sleep" name="avg_sleep" placeholder="Ex: 8 (in hrs)"/>
            </div>
            <div class="input-group mb-3 ml-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-user-phone">Average Heart Rate</span>
              </div>
              <input type="number" class="form-control" v-model="formData.heart_rate" name="heart_rate" placeholder="Ex: 75 (per Minute)"/>
            </div>
          </div>
        </form>
        <button rel="tooltip" title="Update" style="box-shadow: 1px 5px 10px rgba(0,0,0,0.2);" class="btn btn-primary" @click="addUserAnalytics()">Add User Analytics</button>
      </div>
    </div>
    <div class="card" v-for="(list,index) in analytics" v-bind:key="index" style="width: 70%; margin-right: -2.4%; margin-top: 40px;">
      <div class="p-2">
        <div class="" style="width: 100%%;">
          <h6 class="bg-light p-2">User Details</h6>
          <div class="card-body" style="display: flex; justify-content: space-between;">
            <h5 class="card-title">{{ list.full_name }}</h5>
            <h5 class="card-subtitle mb-2 text-muted">{{ list.phone_number }}</h5>
            <h5 class="card-subtitle mb-2 text-muted">{{ list.email_id }}</h5>
          </div>
        </div>
        <div class="col" style="width: 100%;">
          <h6 class="bg-light p-2">{{ list.analytics.length>0?'Health Analysis':'No Health Analysis'}}</h6>
          <div class="bg-light " style="display: flex; width: 100%; margin-bottom: 50px;">
            <div class="main-con" v-for="(val, index) in list.analytics">
              <h5 class="con">{{ new Date(val.created_at).toLocaleString() }}</h5>
              <p class="con">Calorie Intake: {{ val.calorie_intake }}</p>
              <p class="con">Average Sleep: {{ val.avg_sleep }}</p>
              <p class="con">Heart Rate: {{ val.heart_rate }}</p>
              <p class="con">Height: {{ val.height }}</p>
              <p class="con">Weight: {{ val.weight }}</p>
              <p class="con">Condition: <span :style="{'color': val.healthy?'green':'red'}">{{ val.healthy?"healthy":"unhealthy" }}</span></p>
              <h6 style="margin: 10px 0 0 0;">Suggestions: </h6>
              <p class="con">{{ val.description }}</p>
            </div>
          </div>
        </div>
        <p style="margin-left: 20px;" class="card-subtitle mb-2 text-muted">{{ list.calories_burned }}</p>
      </div>
    </div>
  </app-layout>
</template>

<script>
Vue.component("user-analytics", {
  template: "#user-analytics",
  data: () => ({
    users: [],
    activities: [],
    analytics: [],
    formData: [],
  }),
  created() {
    this.fetchActivities()
    this.fetchUsers()
    this.getAnalytics()
  },
  methods: {
    fetchUsers: function () {
      axios.get("/api/users")
          .then(res => this.users = res.data)
          .catch(() => alert("Error while fetching users"));
    },
    fetchActivities: function () {
      axios.get("/api/activities")
          .then(res => {
            this.activities = res.data
          })
          .catch(() => alert("Error while fetching activities"));
    },
    getCalories: function (age){
      if(age>15 && age<20) return 2000;
      else if(age>20 && age<30) return 2500;
      else if(age>30 && age<40) return 3000;
      else if(age>40 && age<50) return 2500;
      else if(age>50 && age<70) return 2000;
      else return 1500;
    },
    addUserAnalytics: function () {
      //form validation
      if (this.formData.user_id == null || this.formData.user_id == "") {
        alert("Please select a user");
        return;
      }
      if (this.formData.calorie_intake == null || this.formData.calorie_intake == "" || this.formData.calorie_intake < 0) {
        alert("Please enter calorie intake");
        return;
      }
      if (this.formData.avg_sleep == null || this.formData.avg_sleep == "" || this.formData.avg_sleep < 0) {
        alert("Please enter average sleep");
        return;
      }
      if (this.formData.heart_rate == null || this.formData.heart_rate == "" || this.formData.heart_rate < 0) {
        alert("Please enter heart rate");
        return;
      }
      if (this.formData.height == null || this.formData.height == "" || this.formData.height < 0) {
        alert("Please enter height");
        return;
      }
      if (this.formData.weight == null || this.formData.weight == "" || this.formData.weight < 0) {
        alert("Please enter weight");
        return;
      }
      if(this.formData.avg_sleep<6 || this.formData.avg_sleep>10){
        alert("Average sleep should be between 6 and 10 hours");
        return;
      }
      if(this.formData.heart_rate<60 || this.formData.heart_rate>100){
        alert("Heart rate should be between 60 and 100");
        return;
      }
      if(this.formData.height<120 || this.formData.height>200){
        alert("Height should be between 150 and 200 cm");
        return;
      }
      if(this.formData.weight<40 || this.formData.weight>100){
        alert("Weight should be between 40 and 100 kg");
        return;
      }
      if(this.formData.calorie_intake>5000 || this.formData.calorie_intake<1000){
        alert("Calorie intake should be between 1000 and 5000");
        return;
      }
      try{
        parseInt(this.formData.calorie_intake)
        parseInt(this.formData.avg_sleep)
        parseInt(this.formData.heart_rate)
        parseInt(this.formData.height)
        parseInt(this.formData.weight)
      }
      catch(e){
        alert("Please enter valid values");
        return;
      }

      let description = '';
      let healthy = false;
      let remainingCalories = this.formData.calorie_intake;
      //get user using formData.user_id
      const user = this.users.find(user => user.id === this.formData.user_id)
      //get activity using formData.activity_id
      const activity = this.activities.find(activity => activity.userId === this.formData.user_id)
      if(activity) remainingCalories -= activity.calories;
      else remainingCalories -= this.getCalories(user.age);
      if(remainingCalories<=1000) {
        healthy = true;
        description = "You are healthy. Keep it up! "
        if(remainingCalories<=500 && remainingCalories<=-500) description += "You can eat more."
        else if(remainingCalories>500) description += "You may Increase weight in near future."
        else {
          healthy = false;
          description = "You are unhealthy. Eat More! "
        }
      } else {
        healthy = false;
        description = "You are unhealthy. "
        if(!activity) description += "You are not doing any activity. Choose an activity and start doing it. "
        else description += "Take care of your diet and do more activity. "
      }
      const url = `/api/analytics`;
      axios.post(url, {
        user_id: this.formData.user_id,
        description: description,
        calorie_intake: this.formData.calorie_intake,
        avg_sleep: this.formData.avg_sleep,
        heart_rate: this.formData.heart_rate,
        healthy: healthy,
        height: this.formData.height,
        weight: this.formData.weight,
        // created_at: new Date().toISOString()
      })
      .then(response => {
        alert("User Analytics Added Successfully");
        this.users.push(response.data)
        this.hideForm= true;
      })
      .catch(error => {
        alert("Error while adding user analytics");
        console.log(error)
      })
    },
    getAnalytics: function () {
      const url = `/api/analytics`;
      axios.get(url)
      .then(response => {
        const data = response.data;
        data.forEach((d) => {
          const { 
            user_id, 
            description, 
            calorie_intake, 
            avg_sleep, 
            heart_rate, 
            healthy, 
            height, 
            weight, 
            created_at,
            address,
            full_name,
            phone_number,
            email_id,
            age,
            gender,
            id,
          } = d;
          let isAdded = false;
          this.analytics.forEach((a) => {
            if(a.id === user_id) {
              isAdded = true;
              a.analytics.push({
                user_id, 
                description, 
                calorie_intake, 
                avg_sleep, 
                heart_rate, 
                healthy, 
                height, 
                weight, 
                created_at: created_at.millis,
              })
              a.analytics.sort((a,b) => {
                return b.created_at - a.created_at;
              })
            }
          });
          if(!isAdded){
            const analytics = [];
            if(user_id){
              analytics.push({
                  user_id, 
                  description, 
                  calorie_intake, 
                  avg_sleep, 
                  heart_rate, 
                  healthy, 
                  height, 
                  weight, 
                  created_at: created_at.millis,
                })
            }
            //check activies if user_id is present
            const activity = this.activities.find(activity => activity.userId === user_id)
            this.analytics.push({
              address,
              full_name,
              phone_number,
              email_id,
              age,
              gender,
              id,
              analytics,
              calories_burned: activity ? "Total Calories Burned: "+activity.calories : "No activity",
            });
          }
        })
      })
      .catch(error => {
        console.log(error)
      })
    },
  }
});
</script>

<style scoped>
  .main-con{
    padding: 20px;
    min-width: 100px;
    border: 1px solid #ccc;
  }
  .con {
    margin: 10px 0 0 0;
    padding: 0;
  }
</style>