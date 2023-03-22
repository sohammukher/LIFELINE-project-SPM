const severityTextColorMap = {
  0: 'text-success',
  1: 'text-info',
  2: 'text-primary',
  3: 'text-warning',
  4: 'text-danger',
};
const severityBgColorMap = {
  0: 'badge badge-pill badge-success bg-success ',
  1: 'badge badge-pill badge-info bg-info ',
  2: 'badge badge-pill badge-primary bg-primary ',
  3: 'badge badge-pill badge-warning bg-warning ',
  4: 'badge badge-pill badge-danger bg-danger ',
};

const app = Vue.createApp({
  data() {
    return {
      headerTitle: 'Welcome to Self Assessment Portal!',
      proceedBtnLabel: 'Proceed',
      proceeded: false,
      qa: [
        {
          index: 0,
          questionLabel: 'Over the past 2 weeks, how often have you been bothered by any of the following problems: <b>Little interest or pleasure in doing things</b>?',
          question: 'Over the past 2 weeks, how often have you been bothered by any of the following problems: Little interest or pleasure in doing things?',
          options: [
            {
              optionName: 'a',
              optionLabel: 'Not At All',
              value: 0
            },
            {
              optionName: 'b',
              optionLabel: 'Several Days',
              value: 1
            },
            {
              optionName: 'c',
              optionLabel: 'More Than Half the Days',
              value: 2
            },
            {
              optionName: 'd',
              optionLabel: 'Nearly Every Day',
              value: 3
            },
          ],
          answer: null,
          defaultAnswer: 'c',
          isAnswered: false
        },
        {
          index: 1,
          questionLabel: 'Over the past 2 weeks, how often have you been bothered by any of the following problems: <b>Feeling down, depressed or hopless</b>?',
          question: 'Over the past 2 weeks, how often have you been bothered by any of the following problems: Feeling down, depressed or hopless?',
          options: [
            {
              optionName: 'a',
              optionLabel: 'Not At All',
              value: 0
            },
            {
              optionName: 'b',
              optionLabel: 'Several Days',
              value: 1
            },
            {
              optionName: 'c',
              optionLabel: 'More Than Half the Days',
              value: 2
            },
            {
              optionName: 'd',
              optionLabel: 'Nearly Every Day',
              value: 3
            },
          ],
          answer: null,
          defaultAnswer: 'c',
          isAnswered: false
        },
        {
          index: 2,
          questionLabel: 'Over the past 2 weeks, how often have you been bothered by any of the following problems: <b>Trouble falling asleep, staying asleep, or sleeping too much</b>?',
          question: 'Over the past 2 weeks, how often have you been bothered by any of the following problems: Trouble falling asleep, staying asleep, or sleeping too much?',
          options: [
            {
              optionName: 'a',
              optionLabel: 'Not At All',
              value: 0
            },
            {
              optionName: 'b',
              optionLabel: 'Several Days',
              value: 1
            },
            {
              optionName: 'c',
              optionLabel: 'More Than Half the Days',
              value: 2
            },
            {
              optionName: 'd',
              optionLabel: 'Nearly Every Day',
              value: 3
            },
          ],
          answer: null,
          defaultAnswer: 'c',
          isAnswered: false
        },
        {
          index: 3,
          questionLabel: 'Over the past 2 weeks, how often have you been bothered by any of the following problems: <b>Feeling tired or having little energy</b>?',
          question: 'Over the past 2 weeks, how often have you been bothered by any of the following problems: Feeling tired or having little energy?',
          options: [
            {
              optionName: 'a',
              optionLabel: 'Not At All',
              value: 0
            },
            {
              optionName: 'b',
              optionLabel: 'Several Days',
              value: 1
            },
            {
              optionName: 'c',
              optionLabel: 'More Than Half the Days',
              value: 2
            },
            {
              optionName: 'd',
              optionLabel: 'Nearly Every Day',
              value: 3
            },
          ],
          answer: null,
          defaultAnswer: 'c',
          isAnswered: false
        },
        {
          index: 4,
          questionLabel: 'Over the past 2 weeks, how often have you been bothered by any of the following problems: <b>Poor appetite or overeating</b>?',
          question: 'Over the past 2 weeks, how often have you been bothered by any of the following problems: Poor appetite or overeating?',
          options: [
            {
              optionName: 'a',
              optionLabel: 'Not At All',
              value: 0
            },
            {
              optionName: 'b',
              optionLabel: 'Several Days',
              value: 1
            },
            {
              optionName: 'c',
              optionLabel: 'More Than Half the Days',
              value: 2
            },
            {
              optionName: 'd',
              optionLabel: 'Nearly Every Day',
              value: 3
            },
          ],
          answer: null,
          defaultAnswer: 'c',
          isAnswered: false
        },
        {
          index: 5,
          questionLabel: 'Over the past 2 weeks, how often have you been bothered by any of the following problems: <b>Feeling bad about yourself - or that you\'re a failure or have let yourself or your family down</b>?',
          question: 'Over the past 2 weeks, how often have you been bothered by any of the following problems: Feeling bad about yourself - or that you\'re a failure or have let yourself or your family down?',
          options: [
            {
              optionName: 'a',
              optionLabel: 'Not At All',
              value: 0
            },
            {
              optionName: 'b',
              optionLabel: 'Several Days',
              value: 1
            },
            {
              optionName: 'c',
              optionLabel: 'More Than Half the Days',
              value: 2
            },
            {
              optionName: 'd',
              optionLabel: 'Nearly Every Day',
              value: 3
            },
          ],
          answer: null,
          defaultAnswer: 'c',
          isAnswered: false
        },
        {
          index: 6,
          questionLabel: 'Over the past 2 weeks, how often have you been bothered by any of the following problems: <b>Trouble concentrating on things, such as reading the newspaper or watching television</b>?',
          question: 'Over the past 2 weeks, how often have you been bothered by any of the following problems: Trouble concentrating on things, such as reading the newspaper or watching television?',
          options: [
            {
              optionName: 'a',
              optionLabel: 'Not At All',
              value: 0
            },
            {
              optionName: 'b',
              optionLabel: 'Several Days',
              value: 1
            },
            {
              optionName: 'c',
              optionLabel: 'More Than Half the Days',
              value: 2
            },
            {
              optionName: 'd',
              optionLabel: 'Nearly Every Day',
              value: 3
            },
          ],
          answer: null,
          defaultAnswer: 'c',
          isAnswered: false
        },
        {
          index: 7,
          questionLabel: 'Over the past 2 weeks, how often have you been bothered by any of the following problems: <b>Moving or speaking so slowly that other people could have noticed. Or, the opposite - being so fidgety or restless that you have been moving around a lot more than usual</b>?',
          question: 'Over the past 2 weeks, how often have you been bothered by any of the following problems: Moving or speaking so slowly that other people could have noticed. Or, the opposite - being so fidgety or restless that you have been moving around a lot more than usual?',
          options: [
            {
              optionName: 'a',
              optionLabel: 'Not At All',
              value: 0
            },
            {
              optionName: 'b',
              optionLabel: 'Several Days',
              value: 1
            },
            {
              optionName: 'c',
              optionLabel: 'More Than Half the Days',
              value: 2
            },
            {
              optionName: 'd',
              optionLabel: 'Nearly Every Day',
              value: 3
            },
          ],
          answer: null,
          defaultAnswer: 'c',
          isAnswered: false
        },
        {
          index: 8,
          questionLabel: 'Over the past 2 weeks, how often have you been bothered by any of the following problems: <b>Thoughts that you would be better off dead or of hurting yourself in some way</b>?',
          question: 'Over the past 2 weeks, how often have you been bothered by any of the following problems: Thoughts that you would be better off dead or of hurting yourself in some way?',
          options: [
            {
              optionName: 'a',
              optionLabel: 'Not At All',
              value: 0
            },
            {
              optionName: 'b',
              optionLabel: 'Several Days',
              value: 1
            },
            {
              optionName: 'c',
              optionLabel: 'More Than Half the Days',
              value: 2
            },
            {
              optionName: 'd',
              optionLabel: 'Nearly Every Day',
              value: 3
            },
          ],
          answer: null,
          defaultAnswer: 'c',
          isAnswered: false
        },
        
      ],
      currentIndex: 0,
      currentQuestion: {},
      finished: false,
    };
  },
  methods: {
    resetCurrentQuestionIndex: function() {
      this.currentQuestion = this.qa.filter(q => q.index === this.currentIndex)[0];
    },
    startAssessment: function() {
      this.proceeded = true;
      this.resetCurrentQuestionIndex();
    },
    goToFirst: function() {
      this.currentIndex = 0;
      this.currentQuestion = this.qa[this.currentIndex];
    },
    goToLast: function() {
      this.currentIndex = this.qa.length - 1;
      this.currentQuestion = this.qa[this.currentIndex];
    },
    next: function() {
      this.currentIndex = this.currentIndex + 1;
      this.currentQuestion = this.qa[this.currentIndex];
    },
    prev: function() {
      this.currentIndex = this.currentIndex - 1;
      this.currentQuestion = this.qa[this.currentIndex];
    },
    selectOption: function(selectedOption) {
      this.qa[this.currentIndex] = {
        ...this.qa[this.currentIndex],
        answer: selectedOption
      };
      this.currentQuestion = this.qa[this.currentIndex];
    },
    finish: function() {
      this.finished = true;
    },
    toHtml: function(inputText) {
      return `<span>${inputText}</span>`;
    },
    saveResult: function() {
      console.log('saving results..');
      if(this.totalScore != null && this.totalScore != undefined) {
        let pathName = '/patient/adityajoshi.sfdc@gmail.com' || window.location.pathname;
        pathName = pathName.endsWith("/") ? pathName.substring(0, pathName.length - 1) : pathName;
        const parts = pathName.split("/");
        const pId = parts[parts.length - 1];
        const dataBody = {
          pId,
          form: this.qa,
          score: this.totalScore
        };
        console.log(dataBody);
        console.log(JSON.stringify(dataBody));
        fetch("http://localhost:8080/saveSelfAssessment", {
          method: "POST",
          body: JSON.stringify(dataBody)
        })
        .then(async (resp) => {
          console.log('resp obtained');
          const respJson = await resp.json();
          console.log(respJson);
          if(respJson && respJson.status) {
			  this.redirectToPatientLandingPage(pId);
		  }
        })
        .catch(err => console.error(err));
      }
    },
    redirectToPatientLandingPage: function(patientId) {
		window.open(`http://localhost:8080/patient/${patientId}`, "_self");
	},
	cancelAssessment: function() {
		let pathName = '/patient/adityajoshi.sfdc@gmail.com' || window.location.pathname;
        pathName = pathName.endsWith("/") ? pathName.substring(0, pathName.length - 1) : pathName;
        const parts = pathName.split("/");
        const pId = parts[parts.length - 1];
		this.redirectToPatientLandingPage(pId);
	}
  },
  computed: {
    numOfAnsweredQuestions() {
      return (
        this.qa.filter(q => {
          return q.answer ? true : false;
        }).length
      );
    },
    isNextDisabled() {
      return (this.currentIndex === (this.qa.length - 1));
    },
    isPrevDisabled() {
      return (this.currentIndex === 0);
    },
    currentAnswer() {
      return this.currentQuestion.answer;
    },
    showCoverPage() {
      return (
        (this.proceeded === false)
      );
    },
    showQuestionSection() {
      return (
        (this.proceeded) && (!this.finished)
      );
    },
    showThankYouPage() {
      return !this.showQuestionSection && this.finished;
    },
    allAnswered() {
      return (this.numOfAnsweredQuestions === this.qa.length)
    },
    totalScore() {
      return (
        this.qa.map((q) => {
          const ans = q.answer;
          if (!ans) return 0;
          else return selectedOption = q.options.filter(op => op.optionName === ans)[0].value;
        })
          .reduce((acc, curr) => acc + curr, 0)
      );
    },
    depressionStatus() {
      if (this.totalScore >= 0 && this.totalScore <= 4) return { label: 'Minimal', level: 0 };
      else if (this.totalScore >= 5 && this.totalScore <= 9) return { label: 'Mild', level: 1 };
      else if (this.totalScore >= 10 && this.totalScore <= 14) return { label: 'Moderate', level: 2 };
      else if (this.totalScore >= 15 && this.totalScore <= 19) return { label: 'High', level: 3 };
      else return { label: 'Severe', level: 4 };
    },
    depressionLevelTextColor() {
      return severityTextColorMap[this.depressionStatus.level];
    },
    totalScoreDisplayClass() {
      return severityBgColorMap[this.depressionStatus.level];
    }
  }
});

app.mount('#app');