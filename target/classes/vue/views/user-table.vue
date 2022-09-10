<template id="user-table">
  <app-layout>
    <div class="card" style="padding: 20px; width: 50%;" >
      <table class="table">
        <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">Healthy</th>
          <th scope="col">UnHealthy</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(user, index) in table">
          <th scope="row">{{ index+1 }}</th>
          <td>{{ user.healthy?getUserName(user.healthy.user_id):"----" }}</td>
          <td>{{ user.unhealthy?getUserName(user.unhealthy.user_id):"----" }}</td>
        </tr>
        </tbody>
      </table>
    </div>
  </app-layout>
</template>

<script>
Vue.component("user-table", {
  template: "#user-table",
  data: () => ({
    healthy: [],
    users: [],
    unhealthy: [],
    table: []
  }),
  created() {
    this.fetchUsers()
    this.getTable()
  },
  methods: {
    fetchUsers: function () {
      axios.get("/api/users")
          .then(res => this.users = res.data)
          .catch(() => alert("Error while fetching users"));
    },
    getTable: function () {
      const url = `/api/analytics/table`;
      axios.get(url)
      .then(response => {
        const data = response.data;
        console.log(data)
        const set = new Set();
        data.forEach((item) => {
          if(set.has(item.user_id) || item.user_id>=1000) return;
          set.add(item.user_id)
          if(item.healthy) {
            this.healthy.push(item)
          } else {
            this.unhealthy.push(item)
          }
        })
        for (let i = 0; i < Math.max(this.healthy.length, this.unhealthy.length); i++) {
          this.table.push({
            healthy: this.healthy[i],
            unhealthy: this.unhealthy[i]
          })
        }
      })
      .catch(error => {
        console.log(error)
      })
    },  
    getUserName: function (id) {
      return this.users.find(user => user.id === id)?.full_name
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