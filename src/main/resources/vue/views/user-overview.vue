<template id="user-overview" xmlns:v-bind="http://www.w3.org/1999/xhtml">
  <app-layout>
    <!-- Card - for adding a new user -->
    <div class="card bg-light mb-3">
      <div class="card-header">
        <div class="row">
          <div class="col-6">
            Users
          </div>
          <div class="col" align="right">
            <button rel="tooltip" title="Add"
                    class="btn btn-info btn-simple btn-link"
                    @click="hideForm =!hideForm">
              <i class="fa fa-plus" aria-hidden="true"></i>
            </button>
          </div>
        </div>
      </div>
      <div class="card-body" :class="{ 'd-none': hideForm}">
        <form id="addUser">

          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-name">Name</span>
            </div>
            <input type="text" class="form-control" v-model="formData.name" name="name" placeholder="Name"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-phone">Phone</span>
            </div>
            <input type="number" class="form-control" v-model="formData.phone" name="phone" placeholder="Phone"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-email">Email</span>
            </div>
            <input type="email" class="form-control" v-model="formData.email" name="email" placeholder="Email"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-age">Age</span>
            </div>
            <input type="number" class="form-control" v-model="formData.age" name="age" placeholder="Age"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-gender">Gender</span>
            </div>
            <input type="text" class="form-control" v-model="formData.gender" name="gender" placeholder="Gender"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-address">Address</span>
            </div>
            <input type="text" class="form-control" v-model="formData.address" name="address" placeholder="Address"/>
          </div>

        </form>
        <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="addUser()">Add User</button>
      </div>
    </div>

    <!-- List Group - displays all the users -->
    <div class="grid">
      <div class="list-group-item d-flex align-items-start"
           v-for="(user,index) in users" v-bind:key="index">

        <div class="card" style="width: 18rem;">
          <div class="card-body">
            <h5 class="card-title">{{ user.full_name }}</h5>
            <h6 class="card-subtitle mb-2 text-muted">{{ user.phone_number }}</h6>
            <p class="card-subtitle mb-2 text-muted">{{ user.email_id }}</p>
            <p class="card-text">Address: {{ user.address }}</p>
            <a @click="deleteUser(user, index)"  class="btn btn-outline-danger">Delete</a>
            <a :href="`/users/${user.id}`" class="btn btn-primary">Edit</a>
          </div>
        </div>
      </div>
    </div>
  </app-layout>
</template>

<script>
Vue.component("user-overview", {
  template: "#user-overview",
  data: () => ({
    users: [],
    formData: [],
    hideForm :true,
  }),
  created() {
    this.fetchUsers();
  },
  methods: {
    fetchUsers: function () {
      axios.get("/api/users")
          .then(res => this.users = res.data)
          .catch(() => alert("Error while fetching users"));
    },
    deleteUser: function (user, index) {
      if (confirm('Are you sure you want to delete this user? This action cannot be undone.', 'Warning')) {
        //user confirmed delete
        const userId = user.id;
        const url = `/api/users/${userId}`;
        axios.delete(url)
            .then(response =>
                //delete from the local state so Vue will reload list automatically
                this.users.splice(index, 1).push(response.data))
            .catch(function (error) {
              console.log(error)
            });
      }
    },
    addUser: function (){
      const url = `/api/users`;
      axios.post(url,
          {
            name: this.formData.name,
            email: this.formData.email
          })
          .then(response => {
            this.users.push(response.data)
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
  .grid{
    display: grid;
    grid-template-columns: auto auto auto;
  }
</style>