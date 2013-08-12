/**
 * 
 */
package com.wuxi.domain;

import java.util.List;

/**
 * 调查类帖子数据集实体类
 * 
 * @author 智佳 罗森
 * 
 */
public class QuestionnairePostWrapper {

	private String createDate;
	private String title;
	private String doProjectId;
	private String depId;
	private String readCount;
	private String endDate;
	private String isAnonymous;
	private String orderId;
	private String isTop;
	private String summary;
	private String surveryId;
	private String author;
	private String isEnabled;
	private String updateDate;
	private String isViewSurveryResult;
	private String isAuditingInputText;
	private String isRootDisplay;
	private String isCenterData;
	private String isOnlyShowNo;
	private List<QuestionnaireQuestionWrapper> questionnaireQuestionWrappers;
	private QuestionnaireAnswerWrapper questionnaireAnswerWrapper;

	/**
	 * @return the questionnaireAnswerWrapper
	 */
	public QuestionnaireAnswerWrapper getQuestionnaireAnswerWrapper() {
		return questionnaireAnswerWrapper;
	}

	/**
	 * @param questionnaireAnswerWrapper
	 *            the questionnaireAnswerWrapper to set
	 */
	public void setQuestionnaireAnswerWrapper(
			QuestionnaireAnswerWrapper questionnaireAnswerWrapper) {
		this.questionnaireAnswerWrapper = questionnaireAnswerWrapper;
	}

	/**
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the doProjectId
	 */
	public String getDoProjectId() {
		return doProjectId;
	}

	/**
	 * @param doProjectId
	 *            the doProjectId to set
	 */
	public void setDoProjectId(String doProjectId) {
		this.doProjectId = doProjectId;
	}

	/**
	 * @return the depId
	 */
	public String getDepId() {
		return depId;
	}

	/**
	 * @param depId
	 *            the depId to set
	 */
	public void setDepId(String depId) {
		this.depId = depId;
	}

	/**
	 * @return the readCount
	 */
	public String getReadCount() {
		return readCount;
	}

	/**
	 * @param readCount
	 *            the readCount to set
	 */
	public void setReadCount(String readCount) {
		this.readCount = readCount;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the isAnonymous
	 */
	public String getIsAnonymous() {
		return isAnonymous;
	}

	/**
	 * @param isAnonymous
	 *            the isAnonymous to set
	 */
	public void setIsAnonymous(String isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the isTop
	 */
	public String getIsTop() {
		return isTop;
	}

	/**
	 * @param isTop
	 *            the isTop to set
	 */
	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary
	 *            the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the surveryId
	 */
	public String getSurveryId() {
		return surveryId;
	}

	/**
	 * @param surveryId
	 *            the surveryId to set
	 */
	public void setSurveryId(String surveryId) {
		this.surveryId = surveryId;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the isEnabled
	 */
	public String getIsEnabled() {
		return isEnabled;
	}

	/**
	 * @param isEnabled
	 *            the isEnabled to set
	 */
	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * @return the updateDate
	 */
	public String getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the isViewSurveryResult
	 */
	public String getIsViewSurveryResult() {
		return isViewSurveryResult;
	}

	/**
	 * @param isViewSurveryResult
	 *            the isViewSurveryResult to set
	 */
	public void setIsViewSurveryResult(String isViewSurveryResult) {
		this.isViewSurveryResult = isViewSurveryResult;
	}

	/**
	 * @return the isAuditingInputText
	 */
	public String getIsAuditingInputText() {
		return isAuditingInputText;
	}

	/**
	 * @param isAuditingInputText
	 *            the isAuditingInputText to set
	 */
	public void setIsAuditingInputText(String isAuditingInputText) {
		this.isAuditingInputText = isAuditingInputText;
	}

	/**
	 * @return the isRootDisplay
	 */
	public String getIsRootDisplay() {
		return isRootDisplay;
	}

	/**
	 * @param isRootDisplay
	 *            the isRootDisplay to set
	 */
	public void setIsRootDisplay(String isRootDisplay) {
		this.isRootDisplay = isRootDisplay;
	}

	/**
	 * @return the isCenterData
	 */
	public String getIsCenterData() {
		return isCenterData;
	}

	/**
	 * @param isCenterData
	 *            the isCenterData to set
	 */
	public void setIsCenterData(String isCenterData) {
		this.isCenterData = isCenterData;
	}

	/**
	 * @return the isOnlyShowNo
	 */
	public String getIsOnlyShowNo() {
		return isOnlyShowNo;
	}

	/**
	 * @param isOnlyShowNo
	 *            the isOnlyShowNo to set
	 */
	public void setIsOnlyShowNo(String isOnlyShowNo) {
		this.isOnlyShowNo = isOnlyShowNo;
	}

	/**
	 * @return the questionnaireQuestionWrappers
	 */
	public List<QuestionnaireQuestionWrapper> getQuestionnaireQuestionWrappers() {
		return questionnaireQuestionWrappers;
	}

	/**
	 * @param questionnaireQuestionWrappers
	 *            the questionnaireQuestionWrappers to set
	 */
	public void setQuestionnaireQuestionWrappers(
			List<QuestionnaireQuestionWrapper> questionnaireQuestionWrappers) {
		this.questionnaireQuestionWrappers = questionnaireQuestionWrappers;
	}

	/**
	 * 问题数据集实体类
	 * 
	 * @author 智佳 罗森
	 * 
	 */
	public class QuestionnaireQuestionWrapper {

		private String description;
		private String questionId;
		private String orderId;
		private String questionType;
		private String surveryId;
		private String optionCount;
		private String hasOther;
		private String isForce;
		private String minSelect;
		private String maxSelect;
		private String colspan;
		private List<QuestionnaireQuestion> questionnaireQuestions;

		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * @param description
		 *            the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}

		/**
		 * @return the questionId
		 */
		public String getQuestionId() {
			return questionId;
		}

		/**
		 * @param questionId
		 *            the questionId to set
		 */
		public void setQuestionId(String questionId) {
			this.questionId = questionId;
		}

		/**
		 * @return the orderId
		 */
		public String getOrderId() {
			return orderId;
		}

		/**
		 * @param orderId
		 *            the orderId to set
		 */
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		/**
		 * @return the questionType
		 */
		public String getQuestionType() {
			return questionType;
		}

		/**
		 * @param questionType
		 *            the questionType to set
		 */
		public void setQuestionType(String questionType) {
			this.questionType = questionType;
		}

		/**
		 * @return the surveryId
		 */
		public String getSurveryId() {
			return surveryId;
		}

		/**
		 * @param surveryId
		 *            the surveryId to set
		 */
		public void setSurveryId(String surveryId) {
			this.surveryId = surveryId;
		}

		/**
		 * @return the optionCount
		 */
		public String getOptionCount() {
			return optionCount;
		}

		/**
		 * @param optionCount
		 *            the optionCount to set
		 */
		public void setOptionCount(String optionCount) {
			this.optionCount = optionCount;
		}

		/**
		 * @return the hasOther
		 */
		public String getHasOther() {
			return hasOther;
		}

		/**
		 * @param hasOther
		 *            the hasOther to set
		 */
		public void setHasOther(String hasOther) {
			this.hasOther = hasOther;
		}

		/**
		 * @return the isForce
		 */
		public String getIsForce() {
			return isForce;
		}

		/**
		 * @param isForce
		 *            the isForce to set
		 */
		public void setIsForce(String isForce) {
			this.isForce = isForce;
		}

		/**
		 * @return the minSelect
		 */
		public String getMinSelect() {
			return minSelect;
		}

		/**
		 * @param minSelect
		 *            the minSelect to set
		 */
		public void setMinSelect(String minSelect) {
			this.minSelect = minSelect;
		}

		/**
		 * @return the maxSelect
		 */
		public String getMaxSelect() {
			return maxSelect;
		}

		/**
		 * @param maxSelect
		 *            the maxSelect to set
		 */
		public void setMaxSelect(String maxSelect) {
			this.maxSelect = maxSelect;
		}

		/**
		 * @return the colspan
		 */
		public String getColspan() {
			return colspan;
		}

		/**
		 * @param colspan
		 *            the colspan to set
		 */
		public void setColspan(String colspan) {
			this.colspan = colspan;
		}

		/**
		 * @return the questionnaireQuestions
		 */
		public List<QuestionnaireQuestion> getQuestionnaireQuestions() {
			return questionnaireQuestions;
		}

		/**
		 * @param questionnaireQuestions
		 *            the questionnaireQuestions to set
		 */
		public void setQuestionnaireQuestions(
				List<QuestionnaireQuestion> questionnaireQuestions) {
			this.questionnaireQuestions = questionnaireQuestions;
		}

		/**
		 * 单个问题实体类
		 * 
		 * @author 智佳 罗森
		 * 
		 */
		public class QuestionnaireQuestion {

			private String serialNumber;
			private String optionValue;
			private String questionId;
			private String optionId;
			private String clickCount;
			private String linkUrl;

			/**
			 * @return the serialNumber
			 */
			public String getSerialNumber() {
				return serialNumber;
			}

			/**
			 * @param serialNumber
			 *            the serialNumber to set
			 */
			public void setSerialNumber(String serialNumber) {
				this.serialNumber = serialNumber;
			}

			/**
			 * @return the optionValue
			 */
			public String getOptionValue() {
				return optionValue;
			}

			/**
			 * @param optionValue
			 *            the optionValue to set
			 */
			public void setOptionValue(String optionValue) {
				this.optionValue = optionValue;
			}

			/**
			 * @return the questionId
			 */
			public String getQuestionId() {
				return questionId;
			}

			/**
			 * @param questionId
			 *            the questionId to set
			 */
			public void setQuestionId(String questionId) {
				this.questionId = questionId;
			}

			/**
			 * @return the optionId
			 */
			public String getOptionId() {
				return optionId;
			}

			/**
			 * @param optionId
			 *            the optionId to set
			 */
			public void setOptionId(String optionId) {
				this.optionId = optionId;
			}

			/**
			 * @return the clickCount
			 */
			public String getClickCount() {
				return clickCount;
			}

			/**
			 * @param clickCount
			 *            the clickCount to set
			 */
			public void setClickCount(String clickCount) {
				this.clickCount = clickCount;
			}

			/**
			 * @return the linkUrl
			 */
			public String getLinkUrl() {
				return linkUrl;
			}

			/**
			 * @param linkUrl
			 *            the linkUrl to set
			 */
			public void setLinkUrl(String linkUrl) {
				this.linkUrl = linkUrl;
			}

		}
	}

	/**
	 * 答案数据集实体类
	 * 
	 * @author 智佳 罗森
	 * 
	 */
	public class QuestionnaireAnswerWrapper extends CommonDataWrapper {

		private String isnull;
		private List<QuestionnaireAnswerDataWrapper> answerDataWrappers;

		/**
		 * @return the isnull
		 */
		public String getIsnull() {
			return isnull;
		}

		/**
		 * @param isnull
		 *            the isnull to set
		 */
		public void setIsnull(String isnull) {
			this.isnull = isnull;
		}

		/**
		 * @return the answerDataWrappers
		 */
		public List<QuestionnaireAnswerDataWrapper> getAnswerDataWrappers() {
			return answerDataWrappers;
		}

		/**
		 * @param answerDataWrappers
		 *            the answerDataWrappers to set
		 */
		public void setAnswerDataWrappers(
				List<QuestionnaireAnswerDataWrapper> answerDataWrappers) {
			this.answerDataWrappers = answerDataWrappers;
		}

		/**
		 * 单个用户答案实体类
		 * 
		 * @author 智佳 罗森
		 * 
		 */
		public class QuestionnaireAnswerDataWrapper {

			private String userName;
			private String resultId;
			private String surveryId;
			private String submitDate;
			private String userHostAddress;
			private List<QuestionnaireAnswerDat> questionnaireAnswerDats;

			/**
			 * @return the userName
			 */
			public String getUserName() {
				return userName;
			}

			/**
			 * @param userName
			 *            the userName to set
			 */
			public void setUserName(String userName) {
				this.userName = userName;
			}

			/**
			 * @return the resultId
			 */
			public String getResultId() {
				return resultId;
			}

			/**
			 * @param resultId
			 *            the resultId to set
			 */
			public void setResultId(String resultId) {
				this.resultId = resultId;
			}

			/**
			 * @return the surveryId
			 */
			public String getSurveryId() {
				return surveryId;
			}

			/**
			 * @param surveryId
			 *            the surveryId to set
			 */
			public void setSurveryId(String surveryId) {
				this.surveryId = surveryId;
			}

			/**
			 * @return the submitDate
			 */
			public String getSubmitDate() {
				return submitDate;
			}

			/**
			 * @param submitDate
			 *            the submitDate to set
			 */
			public void setSubmitDate(String submitDate) {
				this.submitDate = submitDate;
			}

			/**
			 * @return the userHostAddress
			 */
			public String getUserHostAddress() {
				return userHostAddress;
			}

			/**
			 * @param userHostAddress
			 *            the userHostAddress to set
			 */
			public void setUserHostAddress(String userHostAddress) {
				this.userHostAddress = userHostAddress;
			}

			/**
			 * @return the questionnaireAnswerDats
			 */
			public List<QuestionnaireAnswerDat> getQuestionnaireAnswerDats() {
				return questionnaireAnswerDats;
			}

			/**
			 * @param questionnaireAnswerDats
			 *            the questionnaireAnswerDats to set
			 */
			public void setQuestionnaireAnswerDats(
					List<QuestionnaireAnswerDat> questionnaireAnswerDats) {
				this.questionnaireAnswerDats = questionnaireAnswerDats;
			}

			/**
			 * 单个答案实体类
			 * 
			 * @author 智佳 罗森
			 * 
			 */
			public class QuestionnaireAnswerDat {
				private String state;
				private String userName;
				private String optionValue;
				private String resultId;
				private String orderId;
				private String optionText;
				private String submitDate;
				private String questionsId;

				/**
				 * @return the state
				 */
				public String getState() {
					return state;
				}

				/**
				 * @param state
				 *            the state to set
				 */
				public void setState(String state) {
					this.state = state;
				}

				/**
				 * @return the userName
				 */
				public String getUserName() {
					return userName;
				}

				/**
				 * @param userName
				 *            the userName to set
				 */
				public void setUserName(String userName) {
					this.userName = userName;
				}

				/**
				 * @return the optionValue
				 */
				public String getOptionValue() {
					return optionValue;
				}

				/**
				 * @param optionValue
				 *            the optionValue to set
				 */
				public void setOptionValue(String optionValue) {
					this.optionValue = optionValue;
				}

				/**
				 * @return the resultId
				 */
				public String getResultId() {
					return resultId;
				}

				/**
				 * @param resultId
				 *            the resultId to set
				 */
				public void setResultId(String resultId) {
					this.resultId = resultId;
				}

				/**
				 * @return the orderId
				 */
				public String getOrderId() {
					return orderId;
				}

				/**
				 * @param orderId
				 *            the orderId to set
				 */
				public void setOrderId(String orderId) {
					this.orderId = orderId;
				}

				/**
				 * @return the optionText
				 */
				public String getOptionText() {
					return optionText;
				}

				/**
				 * @param optionText
				 *            the optionText to set
				 */
				public void setOptionText(String optionText) {
					this.optionText = optionText;
				}

				/**
				 * @return the submitDate
				 */
				public String getSubmitDate() {
					return submitDate;
				}

				/**
				 * @param submitDate
				 *            the submitDate to set
				 */
				public void setSubmitDate(String submitDate) {
					this.submitDate = submitDate;
				}

				/**
				 * @return the questionsId
				 */
				public String getQuestionsId() {
					return questionsId;
				}

				/**
				 * @param questionsId
				 *            the questionsId to set
				 */
				public void setQuestionsId(String questionsId) {
					this.questionsId = questionsId;
				}

			}
		}
	}

}
