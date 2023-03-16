const app = Vue.createApp({
  data() {
    return {
      headerTitle: 'Welcome to Self Assessment Portal!',
      proceedBtnLabel: 'Proceed',
      proceeded: false,
      qa: [
        {
          index: 0,
          question: 'I feel energized after a good sleep of 8 hours.',
          options: [
            {
              optionName: 'a',
              optionLabel: 'Strongly Agree'
            },
            {
              optionName: 'b',
              optionLabel: 'Agree'
            },
            {
              optionName: 'c',
              optionLabel: 'Can\'t Say'
            },
            {
              optionName: 'd',
              optionLabel: 'Disagree'
            },
            {
              optionName: 'e',
              optionLabel: 'Strongly Disagree'
            },
          ],
          answer: null,
          defaultAnswer: 'c',
          isAnswered: false
        },
        {
          index: 1,
          question: 'I feel anxious when I start any new project.',
          options: [
            {
              optionName: 'a',
              optionLabel: 'Strongly Agree'
            },
            {
              optionName: 'b',
              optionLabel: 'Agree'
            },
            {
              optionName: 'c',
              optionLabel: 'Can\'t Say'
            },
            {
              optionName: 'd',
              optionLabel: 'Disagree'
            },
            {
              optionName: 'e',
              optionLabel: 'Strongly Disagree'
            },
          ],
          answer: null,
          defaultAnswer: 'c',
          isAnswered: false
        },
        {
          index: 2,
          question: 'I can not sleep well if I have a bad day.',
          options: [
            {
              optionName: 'a',
              optionLabel: 'Strongly Agree'
            },
            {
              optionName: 'b',
              optionLabel: 'Agree'
            },
            {
              optionName: 'c',
              optionLabel: 'Can\'t Say'
            },
            {
              optionName: 'd',
              optionLabel: 'Disagree'
            },
            {
              optionName: 'e',
              optionLabel: 'Strongly Disagree'
            },
          ],
          answer: null,
          defaultAnswer: 'c',
          isAnswered: false
        },
        {
          index: 3,
          question: 'I get bothered very easily by the gestures of my friends even if they are not very close to me.',
          options: [
            {
              optionName: 'a',
              optionLabel: 'Strongly Agree'
            },
            {
              optionName: 'b',
              optionLabel: 'Agree'
            },
            {
              optionName: 'c',
              optionLabel: 'Can\'t Say'
            },
            {
              optionName: 'd',
              optionLabel: 'Disagree'
            },
            {
              optionName: 'e',
              optionLabel: 'Strongly Disagree'
            },
          ],
          answer: null,
          defaultAnswer: 'c',
          isAnswered: false
        },
        {
          index: 4,
          question: 'Over last 6 months, I have started feeling lonely.',
          options: [
            {
              optionName: 'a',
              optionLabel: 'Strongly Agree'
            },
            {
              optionName: 'b',
              optionLabel: 'Agree'
            },
            {
              optionName: 'c',
              optionLabel: 'Can\'t Say'
            },
            {
              optionName: 'd',
              optionLabel: 'Disagree'
            },
            {
              optionName: 'e',
              optionLabel: 'Strongly Disagree'
            },
          ],
          answer: null,
          defaultAnswer: 'c',
          isAnswered: false
        },
      ],
      currentIndex: 0,
      currentQuestion: {}
    };
  },
  methods: {
    toggleProceedAction: function() {
      this.proceeded = !this.proceeded;
    },
    resetCurrentQuestionIndex: function() {
      this.currentQuestion = this.qa.filter(q => q.index === this.currentIndex)[0];
    },
    startAssessment: function() {
      this.toggleProceedAction();
      this.resetCurrentQuestionIndex();
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
    getSelectedState: function(questionIndex) {
      return (
        (this.qa.filter(q => q.index === questionIndex))[0].answer ? true : false
      );
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
    }
  }
});

app.mount('#app');