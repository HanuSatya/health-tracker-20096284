<template id="user-overview" xmlns:v-bind="http://www.w3.org/1999/xhtml">
  <app-layout>
    <div class="users">
      <!-- List Group - displays all the users -->
      <div class="user-grid">
        <div class="card user" v-for="(user,index) in users" v-bind:key="index">
          <div class="card-body">
            <h5 class="card-title">{{ user.id+": "+user.full_name }}</h5>
            <h6 class="card-subtitle mb-2 text-muted">Phone: {{ user.phone_number }}</h6>
            <p class="card-subtitle mb-2 text-muted">Email: {{ user.email_id }}</p>
            <p class="card-text card-subtitle">Address: {{ user.address }}</p>
            <div style="margin-top: 20px; float: right;">
              <a @click="deleteUser(user, index)" style="margin-right: 10px; color: red; cursor: pointer;">Delete</a>
              <a :href="`/users/${user.id}`" style="box-shadow: 1px 5px 10px rgba(0,0,0,0.2);" class="btn btn-primary">Edit</a>
            </div>
          </div>
        </div>
      </div>
      <!-- Card - for adding a new user -->
      <div class="card bg-light" style="padding: 0px; margin: 20px 0 30px 40px;">
        <div class="card-header">
          <div class="row">
            <div class="col-6" style="font-weight: 700;">
              Add Users
            </div>
          </div>
        </div>
        <div class="card-body" style="display: flex; flex-direction: column; align-items: flex-end;">
          <form id="addUser" style="width: 100%;">
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
          <button rel="tooltip" title="Update" style="box-shadow: 1px 5px 10px rgba(0,0,0,0.2);" class="btn btn-primary" @click="addUser()">Add User</button>
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
            full_name: this.formData.name,
            email_id: this.formData.email,
            phone_number: this.formData.phone,
            gender: this.formData.gender,
            address: this.formData.address,
            age: this.formData.age
          })
          .then(response => {
            console.log('res', response)
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
  .users{
    display: flex;
    
  }
  .user-grid{
    display: grid;
    grid-template-columns: auto auto;
    gap: 20px;
  }
  .user{
    width: 180px;
    height: 200px;
  }
</style>