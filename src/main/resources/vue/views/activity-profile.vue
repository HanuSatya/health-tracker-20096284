<template id="activity-profile">
  <app-layout>
    <div v-if="noActivityFound">
      <p> We're sorry, we were not able to retrieve this activity.</p>
      <p> View <a :href="'/activities'">all activity</a>.</p>
    </div>
    <div class="card bg-light mb-3" v-if="!noActivityFound">
      <div class="card-header">
        <div class="row">
          <div class="col-6"> Activity Profile </div>
          <div class="col" align="right">
            <button rel="tooltip" title="Update"
                    class="btn btn-info btn-simple btn-link"
                    @click="updateActivity()">
              <i class="far fa-save" aria-hidden="true"></i>
            </button>
            <button rel="tooltip" title="Delete"
                    class="btn btn-info btn-simple btn-link"
                    @click="deleteActivity()">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>
          </div>
        </div>
      </div>
      <div class="card-body">
        <form>

          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-id">Activity ID</span>
            </div>
            <input type="number" class="form-control" v-model="activity.id" name="id" readonly placeholder="Id"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-description">Description</span>
            </div>
            <input type="text" class="form-control" v-model="activity.description" name="description" placeholder="Description"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-duration">Duration</span>
            </div>
            <input type="number" class="form-control" v-model="activity.duration" name="duration" placeholder="Duration"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-calories">Calories</span>
            </div>
            <input type="email" class="form-control" v-model="activity.calories" name="calories" placeholder="Calories"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-started">Started</span>
            </div>
            <input type="date" class="form-control" v-model="activity.started" name="started" placeholder="Started"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-userId">User Id</span>
            </div>
            <input type="text" class="form-control" v-model="activity.userId" name="userId" placeholder="User id"/>
          </div>


        </form>
      </div>
<!--      <div class="card-footer text-left">-->
<!--        <p  v-if="activities.length == 0"> No activities yet...</p>-->
<!--        <p  v-if="activities.length > 0"> Activities so far...</p>-->
<!--        <ul>-->
<!--          <li v-for="activity in activities">-->
<!--            {{ activity.description }} for {{ activity.duration }} minutes-->
<!--          </li>-->
<!--        </ul>-->
<!--      </div>-->
    </div>
  </app-layout>
</template>

<script>
Vue.component("activity-profile", {
  template: "#activity-profile",
  data: () => ({
    activity: null,
    noActivityFound: false,
    activities: [],
  }),
  created: function () {
    const activityId = this.$javalin.pathParams["activity-id"];
    const url = `/api/activities/${activityId}`
    axios.get(url)
        .then(res => this.activity = res.data)
        .catch(error => {
          console.log("No activity found for id passed in the path parameter: " + error)
          this.noActivityFound = true
        })
    // axios.get(url + `/activities`)
    //     .then(res => this.activities = res.data)
    //     .catch(error => {
    //       console.log("No activities added yet (this is ok): " + error)
    //     })
  },
  methods: {
    updateActivity: function () {
      //actvity validation
      if (this.activity.description == null || this.activity.description == "") {
        alert("Please enter a description")
        return
      }
      if (this.activity.duration == null || this.activity.duration == "") {
        alert("Please enter a duration")
        return
      }
      if (this.activity.calories == null || this.activity.calories == "") {
        alert("Please enter a calories")
        return
      }
      if (this.activity.started == null || this.activity.started == "") {
        alert("Please enter a started")
        return
      }
      if (this.activity.userId == null || this.activity.userId == "") {
        alert("Please enter a userId")
        return
      }
      
      const activityId = this.$javalin.pathParams["activity-id"];
      const url = `/api/activities/${activityId}`
      axios.patch(url,
          {
            description: this.activity.description,
            duration: this.activity.duration,
            calories:this.activity.calories,
            started:this.activity.started,
            userId:this.activity.userId
          })
          .then(response =>{
              this.activity.push(response.data); alert("Activity updated successfully")})
          .catch(error => {
            alert("Error updating activity: " + error)
          })
    },
    deleteActivity: function () {
      if (confirm("Do you really want to delete?")) {
        const activityId = this.$javalin.pathParams["activity-id"];
        const url = `/api/activities/${activityId}`
        axios.delete(url)
            .then(response => {
              alert("Activity deleted")
              //display the /activities endpoint
              window.location.href = '/activities';
            })
            .catch(function (error) {
              console.log(error)
              alert("Error deleting activity: " + error)
            });
      }
    }
  }
});
</script>
