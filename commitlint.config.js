module.exports = {
  extends: ["@commitlint/config-conventional"],
  rules: {
    "type-enum": [
      2,
      "always",
      ["feat", "fix", "chore", "docs", "refactor", "test"],
    ],
    "subject-empty": [2, "never"],
    "subject-case": [0], // não forçar case
  },
};
