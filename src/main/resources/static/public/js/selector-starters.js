const selectStarterTagElement = {
  html: {
    starters: document.querySelector(".developers"),
    startersField: document.querySelector(".developer-selected"),
    starterSelected: document.querySelectorAll('.devs'),
    programSelected: document.querySelector('#language-selector'),
  },

  synchronize() {
    this.html.starterSelected = document.querySelectorAll('.devs');
  },


  registerStarterInsertEvent() {
    this.html.starters.addEventListener('change', e => {
      const option = e.target.item(e.target.selectedIndex);
      this.insertStarter({
        name: option.innerText,
        id: option.value
      });
    });
  },

  registerProgramSelected() {
    this.html.programSelected.addEventListener('change', e => {
      const option = e.target.item(e.target.selectedIndex);
      const program = option.value;

      this.findStartersByProgram(program);
    });
  },

  registerDeleteEvent() {

    this.synchronize();

    this.html.starterSelected.forEach(starter => {
      if (starter.getAttribute('event-on'))
        return;

      starter.setAttribute('event-on', true);
      starter.addEventListener('click', e => {
        e.target.innerHTML = '';
        e.target.remove();
      });
    });
  },

  addEventListener() {
    this.registerDeleteEvent();
    this.registerStarterInsertEvent();
    this.registerProgramSelected();
  },

  insertStarter(starter) {
    const dev = `
                <label class='devs btn btn-dark btn-sm m-1' >${starter.name}
                    <svg
                        style="font-size: 17px; pointer-events: none;"
                        stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 1024 1024" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"
                    >
                        <path d="M563.8 512l262.5-312.9c4.4-5.2.7-13.1-6.1-13.1h-79.8c-4.7 0-9.2 2.1-12.3 5.7L511.6 449.8 295.1 191.7c-3-3.6-7.5-5.7-12.3-5.7H203c-6.8 0-10.5 7.9-6.1 13.1L459.4 512 196.9 824.9A7.95 7.95 0 0 0 203 838h79.8c4.7 0 9.2-2.1 12.3-5.7l216.5-258.1 216.5 258.1c3 3.6 7.5 5.7 12.3 5.7h79.8c6.8 0 10.5-7.9 6.1-13.1L563.8 512z">
                        </path>
                    </svg>
                    <input style="visibility: hidden; width: 0; height: 0; padding: 0; margin: 0;" type="checkbox" checked value="${starter.id}" name="starters" readonly />
                </label>
            `;

    const containsStarter = Array.from(this.html.startersField
      .querySelectorAll('input'))
      .map(a => a.value)
      .includes(String(starter.id));

    if (containsStarter) return;

    this.html.startersField.insertAdjacentHTML('beforeend', dev);

    this.registerDeleteEvent();
  },

  async findStartersByProgram(language) {

    let url = `${window.location.protocol}//${window.location.host}/dashboard/turma/json?id=${language}`;

    let { starters } = await fetch(url)
      .then(data => data.json());

    this.html.starters.innerHTML = '';

    this.html.starters.innerHTML = starters.map(starter => `
                <option value="${starter.id}">${starter.name}</option>
            `).join('');


    if (starters.length == 1) {
      this.insertStarter(starters[0]);
    }

    if(!starters.length) {
      this.html.starters.innerHTML = `
        <option disabled selected value> -- Turma não possui Starters registrados -- </option>
      `;
    }
    

    this.html.starters.querySelectorAll('option').forEach(option => {

      option.addEventListener('change ', (e) => {
        e.preventDefault();
      })
    })
  },

  load() {
    const [option] = this.html.programSelected.selectedOptions;
    const program = option.value;
    this.findStartersByProgram(program);
  },


  run() {
    this.addEventListener();
    this.load();
  }
}

selectStarterTagElement.run();