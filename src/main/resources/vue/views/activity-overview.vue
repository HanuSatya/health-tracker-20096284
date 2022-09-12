<template id="activity-overview" xmlns:v-bind="http://www.w3.org/1999/xhtml">
  <app-layout>
    <!-- Card - for adding a new activity -->
    <div class="activities">
      <div class="activity-grid">
        <div class="card activity" v-for="(activity,index) in activities" v-bind:key="index">
          <div class="card-body">
            <h5 class="card-title">{{ activity.description }}</h5>
            <h6 class="card-subtitle mb-2 text-muted">Calories Burned: {{ activity.calories }}</h6>
            <p class="card-subtitle mb-2 text-muted">Activity Duration: {{ activity.duration }}</p>
            <p class="card-subtitle">Stared: {{ activity.started.year }}</p>
            <div style="margin-top: 20px; float: right;">
              <a @click="deleteActivity(activity, index)"  style="margin-right: 10px; color: red; cursor: pointer;">Delete</a>
              <a :href="`/activities/${activity.id}`" style="box-shadow: 1px 5px 10px rgba(0,0,0,0.2);" class="btn btn-primary">Edit</a>
            </div>
          </div>
        </div>
      </div>
      <div class="card bg-light" style="padding: 0px; margin: 20px 0 30px 40px;">
        <div class="card-header">
          <div class="row">
            <div class="col-6" style="font-weight: 700;">
              Add Activity
            </div>
          </div>
        </div>
        <div class="card-body" style="display: flex; flex-direction: column; align-items: flex-end;">
          <form id="addActivity" style="width: 100%;">
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-activity-description">Description</span>
              </div>
              <input type="text" class="form-control" v-model="formData.description" name="description" placeholder="Description"/>
            </div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-activity-duration">Duration</span>
              </div>
              <input type="number" class="form-control" v-model="formData.duration" name="duration" placeholder="Duration"/>
            </div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-activity-calories">Calories</span>
              </div>
              <input type="email" class="form-control" v-model="formData.calories" name="calories" placeholder="Calories"/>
            </div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-activity-started">Started</span>
              </div>
              <input type="date" class="form-control" v-model="formData.started" name="started" placeholder="Started"/>
            </div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-activity-userId">UserId</span>
              </div>
              <input type="text" class="form-control" v-model="formData.userId" name="userId" placeholder="UserId"/>
            </div>
          </form>
          <button rel="tooltip" title="Update" style="box-shadow: 1px 5px 10px rgba(0,0,0,0.2);" class="btn btn-primary" @click="addActivity()">Add Activity</button>
        </div>
      </div>
    </div>
  </app-layout>
</template>

<script>
Vue.component("activity-overview", {
  template: "#activity-overview",
  data: () => ({
    activities: [],
    formData: [],
    hideForm :true,
  }),
  created() {
    this.fetchActivities();
  },
  methods: {
    fetchActivities: function () {
      axios.get("/api/activities")
          .then(res => {
            this.activities = res.data
          })
          .catch(() => alert("Error while fetching activities"));
    },
    deleteActivity: function (activity, index) {
      if (confirm('Are you sure you want to delete this activity? This action cannot be undone.', 'Warning')) {
        //user confirmed delete
        const activityId = activity.id;
        const url = `/api/activities/${activityId}`;
        axios.delete(url)
            .then(response =>
                //delete from the local state so Vue will reload list automatically
                this.activities.splice(index, 1).push(response.data))
            .catch(function (error) {
              console.log(error)
            });
      }
    },
    addActivity: function (){
      const url = `/api/activities`;
      axios.post(url,
          {
            description: this.formData.description,
            duration: this.formData.duration,
            calories: this.formData.calories,
            started: this.formData.started,
            userId: this.formData.userId
          })
          .then(response => {
            this.activities.push(response.data)
            this.hideForm= true;
          })
          .catch(error => {
            console.log(error)
          })
    }
  }
});
</script>

<style scoped>
  .activities{
    display: flex;
  }
  .activity-grid{
    display: grid;
    grid-template-columns: auto auto;
    gap: 20px;
  }
  .activity{
    width: 315px !important;
    height: 200px;
  }
</style>