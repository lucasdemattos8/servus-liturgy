module.exports = {
  extends: ["@commitlint/config-conventional"],
  rules: {
    "type-enum": [
      2,
      "always",
      ["feat", "fix", "chore", "docs", "refactor", "test"],
    ],

    "subject-empty": [2, "never"],
    "subject-case": [0],

    "references-empty": [2, "never"],

    "body-leading-blank": [1, "always"],
  },
};
